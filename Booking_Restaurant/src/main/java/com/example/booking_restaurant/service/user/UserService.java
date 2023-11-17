package com.example.booking_restaurant.service.user;
import com.example.booking_restaurant.domain.User;
import com.example.booking_restaurant.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User findByEmail(String email){
        return userRepository.findUserByEmail(email).orElseThrow(()-> new RuntimeException("Not found!"));
    }
}
