const tableBody = document.getElementById('tableBody');
let bookingStatus;

async function getBookingStatus() {
    const res = await fetch("/api/booking/booking-status")
    return await res.json();
}

window.onload = async () => {
    document.getElementById("menu-food").classList.remove("active");
    document.getElementById("menu-booking").classList.add("active");
    bookingStatus = await getBookingStatus();
    await loadBookings();
    await renderTable();
}

async function renderTable() {
    const response = await fetch('api/booking/getAllBooking');
    const result = await response.json();
    console.log(result)
    renderBookingTable(result);
}

function renderBookingTable(bookings) {
    let bookingTableHTML = '';

    bookings.forEach((booking, index) => {
        // Tạo một dòng mới cho mỗi booking
        bookingTableHTML += `
            <tr>
                <td>${index + 1}</td>
                    <td>
                        ${booking.customerName}
                    </td>          
                    <td>
                        ${booking.customerPhoneNumber}
                    </td>
                    <td>
                        ${booking.customerEmail}
                    </td>
                    <td>
                        ${formattedDateTime}
                    </td>                     
                    <td>
                        ${booking.tableType}
                    </td>
                    <td>
                        ${booking.personNumber}
                    </td>     
                    <td>
                        <label>
                          <select class="form-control"
                                  id="${booking.id}"                         
                                  onchange="onChangeSelect(this)">
                          </select>
                        </label>
                    </td>
                    <td>
                        ${booking.message}
                    </td>         
            </tr>
        `;

    });
    tableBody.innerHTML = bookingTableHTML;
    bookings.forEach(booking => {
        renderSelectStatus(booking);
    })

}

function renderSelectStatus(booking) {
    const select = document.getElementById(booking.id);
    bookingStatus.forEach(item => {
        let option = document.createElement("option")
        option.value = item;
        option.innerHTML = item;
        option.selected = booking.status === item;

        select.appendChild(option);
        select.classList.add(booking.status);
    })
}

async function loadBookings() {
    try {
        const response = await fetch('/api/booking/getAllBooking'); // Thay đổi URL API tùy theo tên thư mục và đường dẫn của bạn
        if (response.ok) {
            const bookings = await response.json();
            renderBookingTable(bookings);
        } else {
            console.error('Lỗi khi tải danh sách hóa đơn.');
        }
    } catch (error) {
        console.error('Lỗi khi gửi yêu cầu tải danh sách hóa đơn:', error);
    }
}

async function onChangeSelect(selectElement) {
    const bookingId = selectElement.id;
    const newStatus = selectElement.value;
    try {
        const response = await fetch(`/api/booking/${bookingId}/${newStatus}`, {
            method: 'PATCH',
        });
        if (response.ok) {
            const message = await response.text();
            webToast.Success({
                status: 'Đã thay đổi Status thành công!',
                message: message,
                delay: 2000,
                align: 'topright'
            })
            await renderTable();
        } else {
            const errorMessage = await response.text();
            webToast.Danger({
                status: 'Đã có lỗi xảy ra!',
                message: errorMessage,
                delay: 2000,
                align: 'topright'
            })
            await renderTable();
        }
    } catch (error) {
        webToast.Danger({
            status: 'Đã có lỗi xảy ra!',
            message: error,
            delay: 2000,
            align: 'topright'
        })
    }
    await renderTable();
}

function formatLocalDateTime(localDateTime) {
    const options = {
        hour: 'numeric',
        minute: 'numeric',
        second: 'numeric',
        day: '2-digit',
        month: '2-digit',
        year: 'numeric'
    };

    return new Intl.DateTimeFormat('en-US', options).format(localDateTime);
}

const bookingTime = new Date("2023-11-17T12:34:56");
const formattedDateTime = formatLocalDateTime(bookingTime);







