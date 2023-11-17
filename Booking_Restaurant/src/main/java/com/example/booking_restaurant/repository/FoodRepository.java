package com.example.booking_restaurant.repository;

import com.example.booking_restaurant.domain.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FoodRepository extends JpaRepository<Food, Long> {

    @Query(value = "SELECT f FROM Food f " +
            "WHERE " +
            "f.foodName LIKE :search OR " +
            "f.description LIKE :search OR " +
            "f.category.name LIKE :search ")
    Page<Food> searchFoods(String search, Pageable pageable);
}
