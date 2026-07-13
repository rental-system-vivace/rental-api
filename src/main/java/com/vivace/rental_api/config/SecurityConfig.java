package com.vivace.rental_api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Cấu hình các đường link công khai: Ai cũng xem được váy, đăng ký, đăng nhập
                .requestMatchers("/api/users/**", "/api/products/**", "/api/rentals/**").permitAll()
                // Cấu hình đường link bảo mật: Bắt buộc phải đăng nhập có Token mới được chốt đơn
                .requestMatchers("/api/orders/checkout").authenticated()
                .anyRequest().permitAll()
            )
            // Kích hoạt thiết bị quét thẻ JWT chạy trước khi xử lý đăng nhập mặc định
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}