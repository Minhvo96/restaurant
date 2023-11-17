package com.example.booking_restaurant.service.foodService.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FoodListResponse {
    private Long id;

    private String foodName;

    private BigDecimal price;

    private String description;

    private String category;

    private String type;

    private List<String> images;
}
