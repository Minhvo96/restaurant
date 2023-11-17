package com.example.booking_restaurant.controller.restController;
import com.example.booking_restaurant.domain.enumration.EBookingStatus;
import com.example.booking_restaurant.domain.enumration.TableType;
import com.example.booking_restaurant.repository.BookingRepository;
import com.example.booking_restaurant.service.bookingService.BookingService;
import com.example.booking_restaurant.service.bookingService.request.BookingSaveRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/booking")
@AllArgsConstructor
public class BookingRestController {

    private final BookingService bookingService;

    private final BookingRepository bookingRepository;

    @PostMapping
    public void createBooking(@RequestBody BookingSaveRequest request){
        bookingService.create(request);
    }

    @PatchMapping("/{id}/{status}")
    public ResponseEntity<?> changeStatus(@PathVariable Long id, @PathVariable String status) {
        bookingService.changeStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/checkVip")
    public ResponseEntity<String> checkVipTableAvailable() {
        long countVipTable = bookingRepository.countByTableTypeAndStatus(TableType.VIP, EBookingStatus.PENDING);

        int availableVipTables = 5;

        if (countVipTable >= availableVipTables) {
            return ResponseEntity.badRequest().body("Đã hết bàn VIP");
        }

        return ResponseEntity.ok("Bàn VIP còn trống!");
    }
}
