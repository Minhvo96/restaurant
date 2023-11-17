package com.example.booking_restaurant.repository;

import com.example.booking_restaurant.domain.FoodImage;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodImageRepository extends JpaRepository<FoodImage, String> {
    @Transactional
    void deleteBookImageByFileUrl(String fileUrl);
}
