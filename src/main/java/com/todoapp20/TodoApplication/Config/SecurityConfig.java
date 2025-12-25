package com.todoapp20.TodoApplication.Config;

import com.todoapp20.TodoApplication.Service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    private final CustomUserDetailsService userDetailsService;
    private final OAuth2SuccessHandler successHandler;
    public SecurityConfig(CustomUserDetailsService userDetailsService, OAuth2SuccessHandler successHandler) {
        this.userDetailsService = userDetailsService;
        this.successHandler = successHandler;
    }
    @Bean public BCryptPasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
    @Bean public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/login.html", "/register.html", "/api/auth/**", "/css/**").permitAll().anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login.html").loginProcessingUrl("/perform_login").defaultSuccessUrl("/", true).permitAll())
                .oauth2Login(oauth -> oauth.loginPage("/login.html").successHandler(successHandler))
                .logout(logout -> logout.logoutSuccessUrl("/login.html"));
        return http.build();
    }
}


