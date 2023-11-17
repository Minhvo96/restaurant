package com.example.booking_restaurant.service.foodService.request;

import com.example.booking_restaurant.service.selectOption.SelectOptionRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FoodSaveRequest {

    private String foodName;

    private String price;

    private String description;

    private String type;

    private SelectOptionRequest category;

    private List<SelectOptionRequest> files;
}
