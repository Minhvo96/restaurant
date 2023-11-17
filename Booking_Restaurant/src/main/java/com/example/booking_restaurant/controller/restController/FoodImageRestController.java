package com.example.booking_restaurant.controller.restController;

import com.example.booking_restaurant.domain.FoodImage;
import com.example.booking_restaurant.service.foodImageService.FoodImageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("api/foodImages")
@RestController
@AllArgsConstructor
public class FoodImageRestController {
    private final FoodImageService foodImageService;

    @PostMapping
    public FoodImage upload(@RequestParam("avatar") MultipartFile avatar) throws IOException {
        return foodImageService.saveAvatar(avatar);
    }

    @DeleteMapping
    public void delete(@RequestParam("url") String url) {
        foodImageService.delete(url);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable String id) {
        foodImageService.deleteById(id);
    }
}
