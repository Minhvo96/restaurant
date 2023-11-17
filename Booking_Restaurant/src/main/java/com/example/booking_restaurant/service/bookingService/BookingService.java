package com.example.booking_restaurant.service.bookingService;
import com.example.booking_restaurant.domain.Booking;
import com.example.booking_restaurant.domain.Customer;
import com.example.booking_restaurant.domain.enumration.EBookingStatus;
import com.example.booking_restaurant.domain.enumration.TableType;
import com.example.booking_restaurant.repository.BookingRepository;
import com.example.booking_restaurant.service.bookingService.request.BookingSaveRequest;
import com.example.booking_restaurant.service.customerService.CustomerService;
import com.example.booking_restaurant.util.AppUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    private final CustomerService customerService;

    public void create(BookingSaveRequest request) {
        Booking booking = AppUtil.mapper.map(request, Booking.class);

        Customer customer = customerService.findByEmail(request.getCustomerEmail());

        booking.setCustomer(customer);
        booking.setStatus(EBookingStatus.PENDING);
        booking.setMessage(request.getMessage());
        booking.setTime(LocalDateTime.parse(request.getTime()));
        booking.setTableType(TableType.valueOf(request.getTableType()));
        booking.setPersonNumber(request.getPersonNumber());

        bookingRepository.save(booking);

    }



    public void changeStatus(Long id, String status) {
        var booking = bookingRepository.findById(id).orElse(new Booking());
        booking.setStatus(EBookingStatus.valueOf(status));
        bookingRepository.save(booking);

    }
}
