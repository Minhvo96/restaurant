package com.example.booking_restaurant.service.customerService;
import com.example.booking_restaurant.domain.Customer;
import com.example.booking_restaurant.repository.CustomerRepository;
import com.example.booking_restaurant.service.customerService.response.CustomerResponse;
import com.example.booking_restaurant.service.selectOption.SelectOptionResponse;
import com.example.booking_restaurant.util.AppUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Customer findByEmail(String email) {
        return customerRepository.findCustomerByEmail(email).orElseThrow(() -> new RuntimeException("Not Found!"));
    }
    public CustomerResponse getCustomerResponseByID(Long id) {
        return customerRepository.findById(id)
                .map(customer -> AppUtil.mapper
                        .map(customer, CustomerResponse.class))
                .orElseThrow(()-> new RuntimeException("Not found"));
    }

    public List<SelectOptionResponse> findAll() {
        return customerRepository.findAll().stream()
                .map(customer -> new SelectOptionResponse(customer.getId().toString(), customer.getName()))
                .collect(Collectors.toList());
    }
}
