package com.example.booking_restaurant.repository;

import com.example.booking_restaurant.domain.Booking;
import com.example.booking_restaurant.domain.enumration.EBookingStatus;
import com.example.booking_restaurant.domain.enumration.TableType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT COUNT(b) " +
            "FROM Booking b " +
            "WHERE b.tableType = :tableType " +
            "AND b.status = :status")
    long countByTableTypeAndStatus(@Param("tableType") TableType tableType, @Param("status") EBookingStatus status);
}
