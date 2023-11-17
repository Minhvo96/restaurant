package com.example.booking_restaurant.domain;

import com.example.booking_restaurant.domain.enumration.ERole;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "users")
@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private ERole role;

    @OneToOne(mappedBy = "user")
    private Customer customer;

}
