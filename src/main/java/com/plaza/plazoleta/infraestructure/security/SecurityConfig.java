package com.plaza.plazoleta.infraestructure.security;

import com.plaza.plazoleta.domain.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    private static final String[] WHITE_LIST_URL = {
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL).permitAll()
                                .requestMatchers("/api/plazoleta/restaurant/create/**").hasRole(Role.ADMIN.name())
                                .requestMatchers("/api/plazoleta/restaurant/all/**").hasRole(Role.CUSTOMER.name())
                                .requestMatchers("/api/plazoleta/menu/restaurant/**").hasRole(Role.CUSTOMER.name())
                                .requestMatchers("/api/plazoleta/order/create/**").hasRole(Role.CUSTOMER.name())
                                .requestMatchers("/api/plazoleta/menu/**").hasRole(Role.OWNER.name())
                                .requestMatchers("/api/plazoleta/employee/**").hasRole(Role.EMPLOYEE.name())
                                .requestMatchers("/api/plazoleta/order/toprepare/**").hasRole(Role.EMPLOYEE.name())
                                .requestMatchers("/api/plazoleta/order/toready/**").hasRole(Role.EMPLOYEE.name())
                                .requestMatchers("/api/plazoleta/order/todeliver/**").hasRole(Role.EMPLOYEE.name())
                                .requestMatchers("/api/plazoleta/order/tocancel/**").hasRole(Role.CUSTOMER.name())
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}


