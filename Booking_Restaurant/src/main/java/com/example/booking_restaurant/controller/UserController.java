package com.example.booking_restaurant.controller;

import com.example.booking_restaurant.domain.enumration.TableType;
import com.example.booking_restaurant.service.foodService.FoodService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping()
@AllArgsConstructor
public class UserController {

    private final FoodService foodService;
    @GetMapping("home")
    public ModelAndView indexUser(@PageableDefault(size = 8) Pageable pageable, @RequestParam(defaultValue = "") String search) {
        ModelAndView view = new ModelAndView("user/index");
        view.addObject("food", foodService.getAll(pageable, search));
        view.addObject("tableType", TableType.values());
        view.addObject("active", "home");
        return view;
    }

    @GetMapping("menu")
    public ModelAndView menu() {
        ModelAndView view = new ModelAndView("user/menu");
        view.addObject("breakfastFood", foodService.getFoodByBreakfast());
        view.addObject("lunchFood", foodService.getFoodByLunch());
        view.addObject("dinnerFood", foodService.getFoodByDinner());
        view.addObject("drinks", foodService.getFoodByDrinks());
        view.addObject("active", "menu");
        return view;
    }

    @GetMapping("about")
    public ModelAndView about() {
        ModelAndView view = new ModelAndView("user/about");
        view.addObject("active","about");
        return view;
    }
//    @GetMapping("blog")
//    public ModelAndView blog() {
//        ModelAndView view = new ModelAndView("login");
//        view.addObject("active","blog");
//        return view;
//    }
//    @GetMapping("contact")
//    public ModelAndView contact() {
//        ModelAndView view = new ModelAndView("403");
//        view.addObject("active","contact");
//        return view;
//    }

    @GetMapping("reservation")
    public ModelAndView reservation() {
        ModelAndView view = new ModelAndView("user/reservation");
        view.addObject("active","reservation");
        view.addObject("tableType", TableType.values());
        return view;
    }

    @GetMapping("/403")
    public ModelAndView accessDenied() {
        return new ModelAndView("user/403");
    }

    @GetMapping("/errors")
    public ModelAndView handleError() {
        return new ModelAndView("user/errors");
    }

}
