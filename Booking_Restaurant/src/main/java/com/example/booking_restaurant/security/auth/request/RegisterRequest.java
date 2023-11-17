package com.example.booking_restaurant.security.auth.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRequest {

    private String name;

    private String phoneNumber;

    private String email;

    private String password;

}
