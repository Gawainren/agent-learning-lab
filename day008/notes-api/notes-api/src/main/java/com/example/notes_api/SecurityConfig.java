package com.example.notes_api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 返回一个新创建的 BCryptPasswordEncoder 对象
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService users(
            PasswordEncoder encoder,
            @Value("${app.auth.user-a-password}") String passwordA,
            @Value("${app.auth.user-b-password}") String passwordB) {

        // 创建 userA ，用户名为 "10"，使用 passwordA
        var userA = User.withUsername("10")
                .password(encoder.encode(passwordA))
                .roles("USER")
                .build();

        // 创建 userB，用户名为 "20"，使用 passwordB
        var userB = User.withUsername("20")
                .password(encoder.encode(passwordB))
                .roles("USER")
                .build();

        // 创建并返回 InMemoryUserDetailsManager，构造参数为 userA、userB
        return new InMemoryUserDetailsManager(userA,userB);
    }
}