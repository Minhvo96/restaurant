package com.example.booking_restaurant.service.foodService;

import com.example.booking_restaurant.domain.Category;
import com.example.booking_restaurant.domain.Food;
import com.example.booking_restaurant.domain.FoodImage;
import com.example.booking_restaurant.repository.FoodImageRepository;
import com.example.booking_restaurant.repository.FoodRepository;
import com.example.booking_restaurant.service.foodService.request.FoodSaveRequest;
import com.example.booking_restaurant.service.foodService.response.FoodDetailResponse;
import com.example.booking_restaurant.service.foodService.response.FoodListResponse;
import com.example.booking_restaurant.service.selectOption.SelectOptionRequest;
import com.example.booking_restaurant.util.AppUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;
    private final FoodImageRepository fileRepository;

    public Page<FoodListResponse> getAll(Pageable pageable, String search) {
        search = "%" + search + "%";
        return foodRepository.searchFoods(search, pageable).map(e -> {
            FoodListResponse result = AppUtil.mapper.map(e, FoodListResponse.class);

            result.setCategory(e.getCategory().getName());

            result.setImages(e.getImages().stream().map(FoodImage::getFileUrl).collect(Collectors.toList()));
            return result;
        });
    }

    public void create(FoodSaveRequest request) {
        var food = AppUtil.mapper.map(request, Food.class);
        food = foodRepository.save(food);

        var files = fileRepository.findAllById(request.getFiles().stream().map(SelectOptionRequest::getId).collect(Collectors.toList()));
        for (var file : files) {
            file.setFood(food);
        }
        fileRepository.saveAll(files);
    }

    public FoodDetailResponse findById(Long id) {
        var food = foodRepository.findById(id).orElse(new Food());
        var result = AppUtil.mapper.map(food, FoodDetailResponse.class);

        result.setCategoryId(food.getCategory().getId());

        List<String> images = food.getImages().stream().map(FoodImage::getFileUrl).collect(Collectors.toList());
        result.setImages(images);
        return result;
    }

    public void update(FoodSaveRequest request, Long id) {
        var foodDb = foodRepository.findById(id).orElse(new Food());
        foodDb.setCategory(new Category());
        AppUtil.mapper.map(request, foodDb);

        foodRepository.save(foodDb);

        var files = fileRepository.findAllById(request.getFiles()
                .stream()
                .map(SelectOptionRequest::getId).collect(Collectors.toList()));
        for (var file : files) {
            file.setFood(foodDb);
        }
        fileRepository.saveAll(files);
    }

    public Boolean delete(Long id) {
        Optional<Food> foodOptional = foodRepository.findById(id);

        if (foodOptional.isPresent()) {
            for (var image : foodOptional.get().getImages()) {
                fileRepository.delete(image);
            }

            foodRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }


    public List<FoodListResponse> getFoodByBreakfast() {
        return foodRepository.findAll().stream()
                .filter(food -> "BREAKFAST".equals(food.getCategory().getName())) // Lọc theo loại "ăn trưa"
                .map(food -> {
                    var result = new FoodListResponse();
                    result.setId(food.getId());
                    result.setFoodName(food.getFoodName());
                    result.setDescription(food.getDescription());
                    result.setPrice(food.getPrice());
                    result.setType(String.valueOf(food.getType()));
                    result.setCategory(String.valueOf(food.getCategory().getName()));

                    List<String> imageUrls = food.getImages().stream()
                            .map(FoodImage::getFileUrl)
                            .collect(Collectors.toList());
                    result.setImages(imageUrls);

                    return result;
                })
                .collect(Collectors.toList());
    }

    public List<FoodListResponse> getFoodByLunch() {
        return foodRepository.findAll().stream()
                .filter(food -> "LUNCH".equals(food.getCategory().getName()))
                .map(food -> {
                    var result = new FoodListResponse();
                    result.setId(food.getId());
                    result.setFoodName(food.getFoodName());
                    result.setDescription(food.getDescription());
                    result.setPrice(food.getPrice());
                    result.setType(String.valueOf(food.getType()));
                    result.setCategory(String.valueOf(food.getCategory().getName()));

                    List<String> imageUrls = food.getImages().stream()
                            .map(FoodImage::getFileUrl)
                            .collect(Collectors.toList());
                    result.setImages(imageUrls);

                    return result;
                })
                .collect(Collectors.toList());
    }

    public List<FoodListResponse> getFoodByDinner() {
        return foodRepository.findAll().stream()
                .filter(food -> "DINNER".equals(food.getCategory().getName()))
                .map(food -> {
                    var result = new FoodListResponse();
                    result.setId(food.getId());
                    result.setFoodName(food.getFoodName());
                    result.setDescription(food.getDescription());
                    result.setPrice(food.getPrice());
                    result.setType(String.valueOf(food.getType()));
                    result.setCategory(String.valueOf(food.getCategory().getName()));

                    List<String> imageUrls = food.getImages().stream()
                            .map(FoodImage::getFileUrl)
                            .collect(Collectors.toList());
                    result.setImages(imageUrls);

                    return result;
                })
                .collect(Collectors.toList());
    }

    public List<FoodListResponse> getFoodByDrinks() {
        return foodRepository.findAll().stream()
                .filter(food -> "DRINKS".equals(food.getCategory().getName()))
                .map(food -> {
                    var result = new FoodListResponse();
                    result.setId(food.getId());
                    result.setFoodName(food.getFoodName());
                    result.setDescription(food.getDescription());
                    result.setPrice(food.getPrice());
                    result.setType(String.valueOf(food.getType()));
                    result.setCategory(String.valueOf(food.getCategory().getName()));

                    List<String> imageUrls = food.getImages().stream()
                            .map(FoodImage::getFileUrl)
                            .collect(Collectors.toList());
                    result.setImages(imageUrls);

                    return result;
                })
                .collect(Collectors.toList());
    }


}
