package com.example.florestaurant.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
//                                .requestMatchers("/admin/**").hasRole("ADMIN")// Cho phép tất cả các yêu cầu mà không cần đăng nhập

                                .requestMatchers("/**").permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")  // Đảm bảo URL của đăng xuất
                        .logoutSuccessUrl("/index")  // Chuyển đến trang chủ sau khi đăng xuất thành công
                        .invalidateHttpSession(true)  // Xóa session khi đăng xuất
                        .clearAuthentication(true)  // Xóa xác thực người dùng khi đăng xuất
                        .permitAll()  // Cho phép tất cả người dùng đăng xuất
                )
                .exceptionHandling(exceptions ->
                        exceptions.accessDeniedPage("/error")  // Đưa trang lỗi tùy chỉnh khi gặp lỗi 403
                );

        return http.build();
    }
}