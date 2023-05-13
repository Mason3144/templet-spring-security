package com.templete.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .headers().frameOptions().sameOrigin() // (1)
                .and() // (2)
                .csrf().disable() // (3)
                .formLogin() // (4)
                .loginPage("/auths/login-form") // (5)
                .loginProcessingUrl("/process_login") // (6)
                .failureUrl("/auths/login-form?error") // (7)
                .and()
                .logout() // (8)
                .logoutUrl("/logout") // (9)
                .logoutSuccessUrl("/") // (10)
                .and()
                .exceptionHandling().accessDeniedPage("/auths/access-denied") // (11)
                .and()
                .authorizeHttpRequests(auth-> auth // (12)
                        .antMatchers("/orders/**").hasRole("ADMIN") // (12-1)
                        .antMatchers("/members/my-page").hasRole("USER") // (12-2)
                        .antMatchers("/**").permitAll() // (12-3)
                );

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();  // (1-1)
    }
}
