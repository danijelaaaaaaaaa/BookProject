package com.example.bookproject.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.requestMatchers("/admin/**").hasRole("ADMIN").
                requestMatchers( "/books/new").hasRole("USER").
                requestMatchers("/", "/auth/login", "/books/list-books","/index").permitAll().anyRequest().permitAll()
                )

                .formLogin(
                        form -> form.loginPage("/auth/login").defaultSuccessUrl("/index",true)
                                .permitAll()).logout(lo -> lo.logoutUrl("/logout").logoutSuccessUrl("/").permitAll()
        );

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
