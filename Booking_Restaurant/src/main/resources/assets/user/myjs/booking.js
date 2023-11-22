const makeReservationButton = document.getElementById("booking-btn");
const eDoneBtn = document.getElementById("done-btn");
const eConfirm_body = document.getElementById("confirm-body");

let data = {};
let checkVip = false;

async function getVipTableAvailable() {
    let res = await fetch("http://localhost:8080/api/booking/checkVip");
    return res;
}

makeReservationButton.addEventListener('click', async function () {
    const eTime = document.getElementById("time").value;
    const ePerson = document.getElementById("person").value;
    const eTableType = document.getElementById("tableType").value;
    const EMessage = document.getElementById("message").value;

    data = {
        time: eTime,
        person: ePerson,
        tableType: eTableType,
        message: EMessage
    }

    const selectedTime = new Date(eTime);
    const currentTime = new Date();

    if (eTime === '') {
        webToast.Danger({
            status: 'Ngày không được để trống!',
            message: 'Hãy chọn ngày cần đặt bàn.',
            delay: 2000,
            align: 'topright'
        })
        return;
    }

    if (new Date(eTime) <= currentTime) {
        webToast.Danger({
            status: 'Ngày giờ không hợp lệ!',
            message: 'Vui lòng không chọn ngày bé hơn hiện tại.',
            delay: 2000,
            align: 'topright'
        })
        return;
    }

    const selectedHour = selectedTime.getHours();
    if (selectedHour < 8 || selectedHour > 21) {
        webToast.Danger({
            status: 'Giờ không hợp lệ!',
            message: 'Vui lòng chọn giờ từ 8h đến 21h.',
            delay: 2000,
            align: 'topright'
        });
        return;
    }

    if (ePerson <= 0 || ePerson > 30) {
        webToast.Danger({
            status: 'Số người không hợp lệ!',
            message: 'Chỉ nhận số lượng khách từ 1-30 người.',
            delay: 2000,
            align: 'topright'
        });
        return;
    }

    if (eTableType === 'NORMAL') {
        await showConfirm(data);
    } else {
        await checkVipTable();
        if (checkVip === false) {
            await showConfirm(data);
        }
    }
})

async function showConfirm(data) {

    if (!customer.email) {
        webToast.Danger({
            status: 'Cần đăng nhập trước!',
            message: 'Hãy đăng nhập để đặt bàn.',
            delay: 3000,
            align: 'topright'
        });
        return;
    }

    const formattedTime = formatDateTime(data.time);

    let bodyHTML =
        `
    <div id="user-detail-container" class="row justify-content-between" style="width: 95%; margin: auto">
                    <h5 class="col-12">Customer detail: </h5>
                    <p class="col-6">Name: <span>${customer.name}</span></p>
                    <p class="col-6">Phone: <span>${customer.phoneNumber}</span></p>
                    <p class="col-6">Email: <span>${customer.email}</span></p>             
                </div>
                <hr style="background-color: #b4bdf1; width: 90%; margin: auto">
                <div id="booking-detail-container" class="row justify-content-between mt-3"
                     style="width: 95%; margin: auto">
                    <h5 class="col-12">Booking detail: </h5>
                    <p class="col-6">Time: <span>${formattedTime}</span></p>
                    <p class="col-6">Table Type: <span> ${data.tableType}</span></p>
                    <p class="col-6">Person: <span>${data.person}</span></p>
                    <p class="col-6">Message: <span>${data.message}</span></p>
                </div>
   
    `
    eConfirm_body.innerHTML = bodyHTML;
    $('#exampleModalLong').modal('show');
}
async function createBill(data) {
    data = {
        customerName: customer.name,
        customerPhoneNumber: customer.phoneNumber,
        customerEmail: customer.email,

        time: data.time,
        personNumber: data.person,
        tableType: data.tableType,
        message: data.message
    }
    // console.log(data);
    const res = await fetch('api/booking', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    if (res.ok) {
        webToast.Success({
            status: 'Bạn đã đặt bàn thành công!',
            message: '',
            delay: 2000,
            align: 'topright'
        })
        $('#exampleModalLong').modal('hide');
    } else {
        webToast.Danger({
            status: 'Đã có lỗi xảy ra!',
            message: '',
            delay: 2000,
            align: 'topright'
        })
    }
}

async function checkVipTable() {
    try {
        const res = await getVipTableAvailable();

        if (!res.ok) {
            webToast.Danger({
                status: 'Hiện tại đang hết bàn VIP!',
                message: '',
                delay: 2000,
                align: 'topright'
            })
            checkVip = true;
        } else {
            checkVip = false;
        }
    } catch (error) {
        webToast.Danger({
            status: 'Đã có lỗi khi call API!',
            message: error,
            delay: 10000,
            align: 'topright'
        })
    }
}


eDoneBtn.onclick = async () => {
    await createBill(data);
}


// Format display time
function formatDateTime(isoString) {
    const options = {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit',
        hour12: false, // 24-hour format
    };

    const date = new Date(isoString);
    return date.toLocaleString('en-GB', options);
}
