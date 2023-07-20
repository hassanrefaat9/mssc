package io.nerd.mssc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    // /api/** authenticated /httpBasic

    @Bean
    @Order(1)
    SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/api/**")
                .authorizeHttpRequests(auth -> {
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    @Order(2)
    SecurityFilterChain H2ConsoleSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher(AntPathRequestMatcher.antMatcher("h2-console/**"))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll();

                })
                .csrf(csrf -> csrf.ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")))
                .headers(headers -> headers.frameOptions(Customizer.withDefaults()).disable())
                .build();
    }

    @Bean
    @Order(3)
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth ->{
                   auth.requestMatchers("/").permitAll();
                   auth.requestMatchers("/error").permitAll();
                   auth.anyRequest().authenticated();
                })
                .formLogin(Customizer.withDefaults())
                .build();
    }
}
