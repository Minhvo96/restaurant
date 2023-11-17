package com.example.booking_restaurant.service.foodService.response;

import com.example.booking_restaurant.domain.enumration.Type;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodDetailResponse {
    private Long id;

    private String foodName;

    private BigDecimal price;

    private String description;

    private Long categoryId;

    private Type type;

    private List<String> images;
}
