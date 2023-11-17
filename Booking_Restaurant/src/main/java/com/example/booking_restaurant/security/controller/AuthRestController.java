package com.example.booking_restaurant.security.controller;
import com.example.booking_restaurant.domain.Customer;
import com.example.booking_restaurant.domain.User;
import com.example.booking_restaurant.security.auth.request.LoginRequest;
import com.example.booking_restaurant.service.customerService.CustomerService;
import com.example.booking_restaurant.service.customerService.response.CustomerResponse;
import com.example.booking_restaurant.service.user.UserService;
import com.example.booking_restaurant.util.AppUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user/api/customer-detail")
@AllArgsConstructor
public class AuthRestController {

    private final CustomerService customerService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<CustomerResponse> checkAuthentication(HttpServletRequest request) {
        // Check if the user is authenticated
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            if (authentication.getPrincipal().equals("anonymousUser")) {
                return ResponseEntity.ok(new CustomerResponse());
            }
            // Retrieve user details from the Authentication object
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Customer customer = customerService.findByEmail(userDetails.getUsername());
            User user = userService.findByEmail(userDetails.getUsername());
            CustomerResponse customerResponse = AppUtil.mapper.map(customer, CustomerResponse.class);
            customerResponse.setRole(user.getRole().toString());
            // Return the user details
            return ResponseEntity.ok(customerResponse);
        } else {
            // User is not authenticated
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }



}
