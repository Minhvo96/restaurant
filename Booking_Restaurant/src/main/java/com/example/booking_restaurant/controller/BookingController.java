package com.example.booking_restaurant.controller;

import com.example.booking_restaurant.domain.enumration.EBookingStatus;
import com.example.booking_restaurant.service.bookingService.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/booking")
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping
    public ModelAndView bill() {
        ModelAndView view = new ModelAndView("admin/booking");

        view.addObject("bookingStatuses", EBookingStatus.values());
        view.addObject("bookings", bookingService.getAllBookings());

        return view;
    }
}
