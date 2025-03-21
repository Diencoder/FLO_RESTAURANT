package com.example.florestaurant.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/**").permitAll()  // Cho phép tất cả các yêu cầu mà không cần đăng nhập
                )
                .formLogin(login -> login
                        .loginPage("/layout/login")  // Chỉ định trang đăng nhập tùy chỉnh
                        .failureUrl("/login?error=true")  // Chỉ định trang nếu đăng nhập thất bại
                        .permitAll()  // Cho phép tất cả người dùng truy cập trang login
                )
                .logout(logout -> logout
                        .permitAll()  // Cho phép tất cả người dùng đăng xuất
                )
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler()); // Đảm bảo sử dụng handler cho lỗi 403

        return http.build();
    }

    // Cung cấp AccessDeniedHandler tùy chỉnh
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        AccessDeniedHandlerImpl handler = new AccessDeniedHandlerImpl();
        handler.setErrorPage("/error");  // Chuyển đến trang lỗi khi gặp lỗi 403
        return handler;
    }
}
