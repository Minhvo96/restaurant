package com.example.booking_restaurant.service.bookingService.request;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BookingSaveRequest {

    private String customerName;

    private String customerPhoneNumber;

    private String customerEmail;

    private String time;

    private String message;

    private String personNumber;

    private String tableType;

    private String status;

}
