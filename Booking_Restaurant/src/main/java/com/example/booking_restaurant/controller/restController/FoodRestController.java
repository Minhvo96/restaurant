package com.example.booking_restaurant.controller.restController;

import com.example.booking_restaurant.service.foodService.FoodService;
import com.example.booking_restaurant.service.foodService.request.FoodSaveRequest;
import com.example.booking_restaurant.service.foodService.response.FoodDetailResponse;
import com.example.booking_restaurant.service.foodService.response.FoodListResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/foods")
@AllArgsConstructor
public class FoodRestController {
    private final FoodService foodService;

    @GetMapping
    public ResponseEntity<Page<FoodListResponse>> list (@PageableDefault(size = 5) Pageable pageable,
                                                        @RequestParam(defaultValue = "") String search) {
        return new ResponseEntity<>(foodService.getAll(pageable, search), HttpStatus.OK);
    }

    @PostMapping
    public void create(@RequestBody FoodSaveRequest request) {
        foodService.create(request);
    }

    @GetMapping("{id}")
    public ResponseEntity<FoodDetailResponse> findById(@PathVariable Long id) {
        return new ResponseEntity<>(foodService.findById(id), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public void updateFood(@RequestBody FoodSaveRequest request, @PathVariable Long id){
        foodService.update(request, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        Boolean isDeleted = foodService.delete(id);
        if (isDeleted) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
