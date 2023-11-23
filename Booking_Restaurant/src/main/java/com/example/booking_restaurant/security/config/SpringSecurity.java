package com.example.booking_restaurant.security.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SpringSecurity {

    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("/register/**").permitAll()
                                .requestMatchers("/403", "/errors").permitAll()
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/food").hasAnyRole("ADMIN")
                                .requestMatchers("/booking").hasAnyRole("ADMIN")
                                .requestMatchers("/menu").permitAll()
                                .requestMatchers("/api/**").permitAll()
                                .requestMatchers("/reservation").permitAll()
                                .requestMatchers("/assets/**").permitAll()
                                .requestMatchers("/home").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .successHandler(customAuthenticationSuccessHandler())
                                .permitAll()
                )
                .logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                                .logoutSuccessUrl("/?message=Logout%20successfully")
                )
                .exceptionHandling()
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.sendRedirect("/403");
                })
                .authenticationEntryPoint((request, response, authException) -> {
                    response.sendRedirect("/errors");
                });
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService) // config để biết class này dùng để check user login
                .passwordEncoder(passwordEncoder()); // loại mã hóa password, check password đã được mã hóa
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();

        successHandler.setDefaultTargetUrl("/home?message=Login%20successfully"); // Set the default target URL
        successHandler.setAlwaysUseDefaultTargetUrl(false); // Ensure the default target URL is always used
        return successHandler;
    }
}