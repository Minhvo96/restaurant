package com.example.booking_restaurant.domain;
import com.example.booking_restaurant.domain.enumration.EBookingStatus;
import com.example.booking_restaurant.domain.enumration.TableType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;


@Table(name = "bookings")
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;

    private String customerPhoneNumber;

    private String customerEmail;

    private LocalDateTime time;

    private String message;

    @Enumerated(EnumType.STRING)
    private TableType tableType;

    private String personNumber;

    @Enumerated(EnumType.STRING)
    private EBookingStatus status;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
