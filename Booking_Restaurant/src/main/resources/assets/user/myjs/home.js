let customer  = {} ;
async function getCurrentCustomer() {
    let res = await fetch("http://localhost:8080/user/api/customer-detail");
    customer = await res.json()
    return customer;
}

function getQueryParam(key) {
    const urlSearchParams = new URLSearchParams(window.location.search);
    return urlSearchParams.get(key);
}

function showMsg() {
    // Check if a "message" query parameter exists in the URL
    const message = getQueryParam("message");

// Show the logout message if it exists
    if (message === null) {
        return;
    } else if (message.includes("successfully")) {
        webToast.Success({
            status: 'Đã đăng nhập thành công!',
            message: 'Chúc quý khách trải nghiệm vui vẻ.',
            delay: 2000,
            align: 'topright'
        })
    } else {
        webToast.Danger({
            status: 'Đăng nhập thất bại!',
            message: 'Vui lòng kiểm tra và đăng nhập lại.',
            delay: 2000,
            align: 'topright'
        })
    }
}

window.onload = async () => {
    await getCurrentCustomer();
    console.log(customer)

    await handleLogBtn();
    showMsg();
}

async function handleLogBtn() {
    const loginBtn = document.getElementById("log-btn");
    const eRegisterLi = document.getElementById("menu-register");

    if (customer.email === null) {
        loginBtn.innerText = "Login";
        loginBtn.href = "javascript:void(0)"; // Remove the "href" attribute
        // loginBtn.onclick = () => {
        //     showLogin();
        // };
        eRegisterLi.innerHTML = `<a href="/register" class="nav-link">Register</a>`;
    } else {
        // Check the role and update accordingly
        if (customer.role === "ROLE_USER") {
            // If the role is "user", display the user's name and a logout button
            loginBtn.innerText = `Logout [Welcome: ${customer.name}]`;
            localStorage.setItem("id", customer.id)
        } else {
            // If the role is "admin", display "Logout" as usual
            loginBtn.innerText = "Logout";
        }

        loginBtn.href = "/logout"; // Update the "href" attribute for logout
        loginBtn.onclick = null; // Remove the click event handler
        eRegisterLi.innerHTML = "";

        if (customer.role === "ROLE_ADMIN") {
            eRegisterLi.innerHTML = `<li class="nav-item"><a style="color: white" href="/food">Dashboard</a></li>`;
        }
    }
}

// validate register
function validatePhoneNumber(phoneNumber) {
    const re = /^[0-9]+$/;
    return re.test(phoneNumber);
}

function validateEmail(email) {
    const re = /\S+@\S+\.\S+/;
    return re.test(email);
}

function validateFullName(fullName) {
    const re = /^[^\d\s]+(\s+[^\d\s]+)*$/;
    return re.test(fullName);
}

function validateForm() {
    const phoneNumber = document.getElementById("phoneNumber").value;
    const email = document.getElementById("email").value;
    const fullName = document.getElementById("fullName").value;
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword").value;

    let errorMessage = "";

    if (!validateFullName(fullName)) {
        errorMessage += "- FULL NAME can't contain numbers or blank.<br>";
    }

    if (!validatePhoneNumber(phoneNumber)) {
        errorMessage += "- Unvalid PHONE NUMBER (numbers only).<br>";
    }

    if (!validateEmail(email)) {
        errorMessage += "- Unvalid EMAIL (abc123@example.com).";
    }

    if (password !== confirmPassword) {
        errorMessage += "- PASSWORD do not match.<br>";
    }

    if (password == null || confirmPassword == null) {
        errorMessage += "- Please fill Password/Confirm Password input<br>";
    }

    if (errorMessage !== "") {
        webToast.Danger({
            status: errorMessage,
            message: 'error',
            delay: 6000,
            align: 'topright'
        });
        return false;
    }

    return true;
}
$('#phoneNumber').on('click', () => {
    $('.text-danger').addClass('hide')
})

$('#email').on('click', () => {
    $('.text-danger').addClass('hide')
})
