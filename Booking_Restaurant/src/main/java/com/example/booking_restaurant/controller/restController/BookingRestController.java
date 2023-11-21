package com.example.booking_restaurant.controller.restController;

import com.example.booking_restaurant.domain.enumration.EBookingStatus;
import com.example.booking_restaurant.domain.enumration.TableType;
import com.example.booking_restaurant.repository.BookingRepository;
import com.example.booking_restaurant.repository.CustomerRepository;
import com.example.booking_restaurant.service.bookingService.BookingService;
import com.example.booking_restaurant.service.bookingService.request.BookingSaveRequest;
import com.example.booking_restaurant.service.bookingService.response.BookingListResponse;
import com.example.booking_restaurant.service.selectOption.SelectOptionResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/booking")
@AllArgsConstructor
public class BookingRestController {

    private final BookingService bookingService;

    private final BookingRepository bookingRepository;

    private final CustomerRepository customerRepository;


    @GetMapping("/getAllBooking")
    public List<BookingListResponse> getAllBooking() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/customers")
    public List<SelectOptionResponse> getAllCustomers() {
        return customerRepository.findAll().stream().map(customer -> new SelectOptionResponse(customer.getId().toString(), customer.getName())).collect(Collectors.toList());
    }

    @PostMapping
    public void createBooking(@RequestBody BookingSaveRequest request) {
        bookingService.create(request);
    }

    @GetMapping("/booking-status")
    public ResponseEntity<EBookingStatus[]> getBillStatus() {
        return new ResponseEntity<>(EBookingStatus.values(), HttpStatus.OK);
    }

    @PatchMapping("/{id}/{status}")
    public ResponseEntity<?> changeStatus(@PathVariable Long id, @PathVariable String status) {
        bookingService.changeStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/checkVip")
    public ResponseEntity<String> checkVipTableAvailable() {
        long countVipTable = bookingRepository.countByTableTypeAndStatus(TableType.VIP, EBookingStatus.PENDING);

        int availableVipTables = 3;

        if (countVipTable >= availableVipTables) {
            return ResponseEntity.badRequest().body("Đã hết bàn VIP!");
        } else {
            return ResponseEntity.ok("Bàn VIP còn trống!");
        }
    }


}
