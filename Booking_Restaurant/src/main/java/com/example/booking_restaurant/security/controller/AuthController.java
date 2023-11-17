package com.example.booking_restaurant.security.controller;
import com.example.booking_restaurant.security.auth.AuthService;
import com.example.booking_restaurant.security.auth.request.RegisterRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@AllArgsConstructor
@Controller
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public String showLogin() {
        return "/user/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // create model object to store form data
        RegisterRequest user = new RegisterRequest();
        model.addAttribute("user", user);
        return "user/register";
    }

    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute("user") RegisterRequest request,
                               BindingResult result,
                               Model model) {
        authService.checkEmail(request, result);
        model.addAttribute("user",request);
        if (result.hasErrors()) {
            return "/user/register";
        }
        authService.register(request);
        return "redirect:/user/login";
    }

}