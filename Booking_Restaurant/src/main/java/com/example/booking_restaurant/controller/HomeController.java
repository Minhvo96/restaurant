package com.example.booking_restaurant.controller;

import com.example.booking_restaurant.service.categoryService.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/food")
@AllArgsConstructor
public class HomeController {

    private final CategoryService categoryService;
    @GetMapping
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("admin/index");
        view.addObject("categories", categoryService.findAll());
        return view;
    }
}
