package com.example.booking_restaurant.repository;

import com.example.booking_restaurant.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
