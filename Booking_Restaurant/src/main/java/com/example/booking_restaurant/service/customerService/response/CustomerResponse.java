package com.example.booking_restaurant.service.customerService.response;

import lombok.*;

@NoArgsConstructor
@Builder
@Getter
@Setter
@AllArgsConstructor
public class CustomerResponse {
    private Long id;

    private String name;

    private String phoneNumber;

    private String email;

    private String role;
}
