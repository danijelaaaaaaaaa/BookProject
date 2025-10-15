package com.example.bookproject.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Autowired
    private CustomLoginSuccessHandler customLoginSuccessHandler;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers( "/", "/index", "/auth/login", "/auth/process",  "/books/details/**", "/books/list-books",
                                "/author/details/**", "/css/**", "/js/**", "/images/**", "/auth/register"

                        ).permitAll()

                        .requestMatchers("/books/**").hasAnyRole("USER", "ADMIN")

                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/process")
                        .successHandler(customLoginSuccessHandler)
                        //.defaultSuccessUrl("/")
                        .failureUrl("/auth/login?error=true")
                        .permitAll()
                )
                .logout(lo -> lo
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/auth/login?logout=true")

                        .permitAll()
                )
                .exceptionHandling(ex -> ex.accessDeniedPage("/auth/access-denied"))
        ;
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/WEB-INF/**");
    }

}
