package com.example.booking_restaurant.domain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Table(name = "customers")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phoneNumber;

    private String email;

    @OneToMany(mappedBy = "customer")
    private List<Booking> bookings;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Customer(Long id) {
        this.id = id;
    }
}
