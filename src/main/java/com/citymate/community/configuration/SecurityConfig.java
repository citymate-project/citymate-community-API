package com.citymate.community.configuration;


import com.citymate.community.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Swagger
                        .requestMatchers("/swagger-ui.html", "/openapi-community.yaml", "/webjars/**").permitAll()
                        // Routes publiques (guest)
                        .requestMatchers(HttpMethod.GET, "/api/forum/categories").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/forum/discussions").permitAll()
                        // Routes admin uniquement
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        // Tout le reste nécessite d'être connecté (client ou admin)
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable());

        return http.build();
    }
}


