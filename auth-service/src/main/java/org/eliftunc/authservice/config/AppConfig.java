package org.eliftunc.authservice.config;

import lombok.RequiredArgsConstructor;
import org.eliftunc.authservice.entity.AuthUserDetails;
import org.eliftunc.client.UserServiceClient;
import org.eliftunc.dto.UserResponseDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final UserServiceClient userServiceClient;

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            UserResponseDto userDto = userServiceClient.getUserByEmail(email);
            if(userDto == null){
                throw new UsernameNotFoundException(email);
            }
            return new AuthUserDetails(userDto, passwordEncoder());
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
