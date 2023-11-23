const foodForm = document.getElementById('foodForm');
const tBody = document.getElementById('tBody');
const ePagination = document.getElementById('pagination');
const eSearch = document.getElementById('search');
const formBody = document.getElementById('formBody');
const input = document.getElementById("file");

let foodSelected = {};
let pageable = {
    page: 1,
    search: ''
}
let categories;
let foods = [];
let idImages = [];
let check = true;

async function getList() {

    const response = await fetch(`api/foods?page=${pageable.page - 1 || 0}&search=${pageable.search || ''}`);

    if (!response.ok) {
        throw new Error("Failed to fetch data");
    }

    const result = await response.json();
    pageable = {
        ...pageable,
        ...result
    };
    genderPagination();
    foods = result.content;
    await renderTBody(foods);
    return result;
}
async function renderTBody(items) {
    let str = '';
    items.forEach(e => {
        str += renderItemStr(e);
    })
    tBody.innerHTML = str;
}
function renderItemStr(item) {
    const imagesHTML = item.images.map(imageUrl => `<img src="${imageUrl}" alt="" />`).join('');
    return `<tr>
                    <td>
                        ${item.id}
                    </td>
                    <td title="${item.description}">
                        ${item.foodName}
                    </td>
                    <td>
                        ${item.description}
                    </td>
                    <td class="image-container">
                        ${imagesHTML}
                    </td>      
                    <td>
                        ${item.price}
                    </td>
                    <td>
                        ${item.type}
                    </td>
                    <td>
                        ${item.category}
                    </td>             
                    <td>
                         <div class="dropdown">
                             <button type="button" class="btn p-0 dropdown-toggle hide-arrow" data-bs-toggle="dropdown">
                             <i class="bx bx-dots-vertical-rounded"></i>
                            </button>
                        <div class="dropdown-menu">
                        <button class="dropdown-item" onclick="showEdit(${item.id})"
                        data-bs-toggle="modal" data-bs-target="#staticBackdrop"
                        ><i class="bx bx-edit-alt me-1"></i> Edit</button
                            >
                        <button class="dropdown-item" onclick="deleteFood(${item.id})"
                        ><i class="bx bx-trash me-1"></i> Delete</button
                        >
              </div>
            </div>
                    </td>
                </tr>`
}
window.onload = async () => {
    categories = await getCategoriesSelectOption();
    await getList();
    renderForm(formBody, getDataInput());
}
function getDataInput() {
    return [
        {
            label: 'Food Name',
            name: 'foodName',
            value: foodSelected.foodName,
            required: true,
            pattern: "^[A-Za-zÀ-Ỷà-ỷẠ-Ỵạ-ỵĂăÂâĐđÊêÔôƠơƯưỨứỪừỰựỬửỮữỨứỬửỰựỦủỤụỠỡỞởỢợỞởỚớỔổỒồỐốỎỏỊịỈỉỌọỈỉỊịỆệỄễỀềẾếỂểỈỉỄễỆệỂểỀề0-9 ,.()]{6,100}",
            message: "Food Name must have minimum is 6 characters and maximum is 20 characters",
        },
        {
            label: 'Price',
            name: 'price',
            value: foodSelected.price,
            required: true,
            pattern: "[1-9][0-9]{1,10}",
            message: 'Price errors'
        },
        {
            label: "Type",
            name: 'type',
            value: foodSelected.type,
            required: true,
            type: 'select',
            options: [{value:"SAVORY", name: "SAVORY"}, {value:"VEGETARIAN", name: "VEGETARIAN"}, {value:"DRINKS", name: "DRINKS"}],
            message: 'Please choose Type'
        },
        {
            label: 'Description',
            name: 'description',
            value: foodSelected.description,
            pattern: "^[A-Za-zÀ-Ỷà-ỷẠ-Ỵạ-ỵĂăÂâĐđÊêÔôƠơƯưỨứỪừỰựỬửỮữỨứỬửỰựỦủỤụỠỡỞởỢợỞởỚớỔổỒồỐốỎỏỊịỈỉỌọỈỉỊịỆệỄễỀềẾếỂểỈỉỄễỆệỂểỀề0-9 ,.()]{6,100}",
            message: "Description must have minimum is 6 characters and maximum is 20 characters",
            required: true
        },
        {
            label: 'Category',
            name: 'category',
            value: foodSelected.categoryId,
            type: 'select',
            required: true,
            options: categories,
            message: 'Please choose Category'
        },
    ];
}
function getDataFromForm(form) {
    const data = new FormData(form);
    return Object.fromEntries(data.entries())
}

foodForm.onsubmit = async (e) => {
    e.preventDefault();
    let data = getDataFromForm(foodForm);
    data = {
        ...data,
        category: {
            id: data.category
        },
        id: foodSelected.id,
        files: idImages.map(e => {
            return{
                id: e
            }
        })
    };

    if(foodSelected.id) {
        await editFood(data);
        webToast.Success({
            status: 'Sửa món ăn thành công!',
            message: '',
            delay: 2000,
            align: 'topright'
        });
    } else {
        await createFood(data);
        webToast.Success({
            status: 'Thêm món ăn mới thành công!',
            message: '',
            delay: 2000,
            align: 'topright'
        })

    }
    $('#staticBackdrop').modal('hide');
    await getList();
}

const searchInput = document.querySelector('#search');
searchInput.addEventListener('search', () => {
    onSearch(event)
});
const onSearch = (e) => {
    e.preventDefault()
    pageable.search = eSearch.value;
    pageable.page = 1;
    getList();
}

async function createFood(data) {

    const res = await fetch('api/foods', {
        method: 'POST',
        headers:{
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })

}
function showCreate() {
    $('#staticBackdropLabel').text('Create Food');
    clearForm();
    renderForm(formBody,getDataInput())
}
const findById = async (id) => {
    const response = await fetch('/api/foods/' + id);
    return await response.json();
}
async function showEdit(id) {
    $('#staticBackdropLabel').text('Edit Food');
    clearForm();
    foodSelected = await findById(id);

    showImgInForm(foodSelected.images);
    renderForm(formBody, getDataInput());
}
async function editFood(data) {
    console.log(data)
    const res = await fetch('/api/foods/' + data.id, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
}
async function deleteFood(id) {
    const confirmBox = webToast.confirm("Are you sure to delete Food " + id + "?");
    confirmBox.click(async function(){
        const res = await fetch('/api/foods/' + id, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(id)
        });
        if (res.ok) {
            webToast.Success({
                status: 'Xóa thành công!',
                message: '',
                delay: 2000,
                align: 'topright'
            });
            await getList();
        } else {
            webToast.Danger({
                status: 'Xóa thất bại!',
                message: '',
                delay: 2000,
                align: 'topright'
            });
        }
});
}
async function getCategoriesSelectOption() {
    const res = await fetch('api/categories');
    return await res.json();
}
const genderPagination = () => {
    ePagination.innerHTML = '';
    let str = '';
    //generate preview truoc
    str += `<li class="page-item ${pageable.first ? 'disabled' : ''}">
              <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Previous</a>
            </li>`

    //generate 1234
    for (let i = 1; i <= pageable.totalPages; i++) {
        str += ` <li class="page-item ${(pageable.page) === i ? 'active' : ''}" aria-current="page">
      <a class="page-link" href="#">${i}</a>
    </li>`
    }

    //generate next truoc
    str += `<li class="page-item ${pageable.last ? 'disabled' : ''}">
              <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Next</a>
            </li>`
    //generate 1234
    ePagination.innerHTML = str;

    const ePages = ePagination.querySelectorAll('li'); // lấy hết li mà con của ePagination
    const ePrevious = ePages[0];
    const eNext = ePages[ePages.length-1]

    ePrevious.onclick = () => {
        if(pageable.page === 1){
            return;
        }
        pageable.page -= 1;
        getList();
    }
    eNext.onclick = () => {
        if(pageable.page === pageable.totalPages){
            return;
        }
        pageable.page += 1;
        getList();
    }
    for (let i = 1; i < ePages.length - 1; i++) {
        if(i === pageable.page){
            continue;
        }
        ePages[i].onclick = () => {
            pageable.page = i;
            getList();
        }
    }
}
function clearForm() {
    foodForm.reset();
    foodSelected = {};

    idImages = [];
    const imgEle = document.getElementById("images");
    const imgOld = imgEle.querySelectorAll("img, button");
    Array.from(imgOld).forEach((img) => {
        img.remove();
    });

    const imgDefault = document.createElement("img");
    imgDefault.src = "../assets/admin/img/img.png";
    imgDefault.classList.add("avatar-preview");
    imgDefault.style = "height: 100px; width: 100px";
    imgEle.append(imgDefault);
}


async function previewImage(evt) {
    if (evt.target.files.length === 0){
        return;
    }

    if(check) {
        idImages = [];
    }

    const files = evt.target.files;
    for (let i = 0; i < files.length; i++){
        webToast.Success({
            status: `Uploading image ${i + 1} / ${files.length} . . .`,
            message: '',
            delay: 1000,
            align: 'topright'
        });
        const file = files[i];
        await previewImageFile(file);

        if (file){
            disableSaveChangesButton();
            //tao formData va them file duoc chon
            const formData = new FormData();
            formData.append("avatar", file);
            formData.append("fileType", "image");

            try{
                const response = await fetch("api/foodImages", {
                    method: "POST",
                    body: formData
                });

                if (response.ok){
                    const result = await response.json();
                    check = false;

                    if (result && result.id) {
                        const id = result.id;
                        const imgEle = document.getElementById("images");
                        const imageContainer = imgEle.lastChild;

                        idImages.push(id);

                        const deleteButton = imageContainer.querySelector(".delete-button");
                        deleteButton.addEventListener("click", () => {
                            const input = document.getElementById("file");
                            input.disabled = true;
                            deleteImage(id);
                            imageContainer.remove();
                        });
                    } else {
                        console.error("Image ID not found in the response");
                    }
                } else {
                    console.error("Failed to upload image:", response.statusText);
                }

            }catch (error){
                console.error("An error occurred:", error);
            }
            webToast.Success({
                status: `Upload completed !`,
                message: '',
                delay: 1000,
                align: 'topright'
            });
            enableSaveChangesButton();
        }
    }
}
async function previewImageFile(file){
    const reader = new FileReader();
    reader.onload = function (){
        const imgEle = document.getElementById("images");

        const imageContainer = document.createElement('div');
        imageContainer.classList.add('imageContainer');

        const img = document.createElement("img");

        img.src = reader.result;
        img.classList.add("avatar-preview");
        imageContainer.append(img);

        let deleteButton = document.createElement('button');
        deleteButton.classList.add('btn-close');
        deleteButton.classList.add('delete-button');
        imageContainer.append(deleteButton);
        imgEle.append(imageContainer);

    };
    reader.readAsDataURL(file);
}
async function deleteImage(id) {
    try {
        const response = await fetch(`api/foodImages/${id}`, {
            method: "DELETE"
        });

        if (response.ok) {
            const input = document.getElementById("file");
            input.disabled = false;
            console.log("Image deleted from the database.");
        } else {
            console.error("Failed to delete image from the database:", response.statusText);
        }
    } catch (error) {
        console.error("An error occurred:", error);
    }
}
function showImgInForm(images) {
    const imgEle = document.getElementById("images");
    const input = document.getElementById("file");
    const imgOld = imgEle.querySelectorAll("img");
    for (let i = 0; i < imgOld.length; i++) {
        imgEle.removeChild(imgOld[i])
    }

    const avatarDefault = document.createElement('img');
    avatarDefault.src = '/assets/admin/img/img.png';
    avatarDefault.classList.add('avatar-preview');
    imgEle.append(avatarDefault)

    images.forEach((img, index) => {
        let imageContainer = document.createElement('div');
        imageContainer.classList.add('imageContainer');
        let image = document.createElement('img');

        image.src = img;
        image.classList.add('avatar-preview');
        imageContainer.append(image);

        let deleteButton = document.createElement('button');
        deleteButton.classList.add('delete-button');
        deleteButton.classList.add('btn-close');
        imageContainer.append(deleteButton);

        deleteButton.addEventListener('click', () => {

            input.disabled = true;
            removeImage(index); // Gọi hàm xóa ảnh khi click vào dấu X
            imageContainer.remove(); // Xóa container chứa ảnh và nút X
        });

        imgEle.append(imageContainer);
    });
}
async function removeImage(index) {
    try {
        const imgToDelete = foodSelected.images[index];

        const formData = new FormData;
        formData.append("url", imgToDelete);

        const response = await fetch("api/foodImages", {
            method: 'DELETE',
            body:formData,
        });

        if (response.ok) {
            // Xóa ảnh khỏi mảng images
            foodSelected.images.splice(index, 1);

            input.disabled = false;
            console.log('Image deleted successfully');
        } else {
            console.error('Error deleting image:', response.status);
        }
    } catch (error) {
        console.error('Error deleting image:', error);
    }
}
function disableSaveChangesButton() {
    const saveChangesButton = document.getElementById('saveChangesButton');
    saveChangesButton.disabled = true;
}
function enableSaveChangesButton() {
    const saveChangesButton = document.getElementById('saveChangesButton');
    saveChangesButton.disabled = false;
}


