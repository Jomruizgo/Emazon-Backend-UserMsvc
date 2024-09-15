package com.emazon.msvc_user.configuration.security;

import com.emazon.msvc_user.adapters.driven.encoder.EncoderAdapter;
import com.emazon.msvc_user.adapters.driven.token.jwt.JavaJwtConfig;
import com.emazon.msvc_user.adapters.driving.http.security.authorization.UserDetailServiceImpl;
import com.emazon.msvc_user.adapters.util.UserDetailUtil;
import com.emazon.msvc_user.configuration.security.jwt.JwtValidatorFilter;
import com.emazon.msvc_user.domain.spi.IUserPersistencePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final EncoderAdapter encoderAdapter= new EncoderAdapter();
    private final IUserPersistencePort userPersistencePort;
    private final UserDetailUtil userDetailUtil;
    private JavaJwtConfig javaJwtConfig;

    public WebSecurityConfig(IUserPersistencePort userPersistencePort, UserDetailUtil userDetailUtil, JavaJwtConfig javaJwtConfig) {
        this.userPersistencePort = userPersistencePort;
        this.userDetailUtil = userDetailUtil;
        this.javaJwtConfig = javaJwtConfig;
    }

    @Bean
    public UserDetailServiceImpl userDetailsService() {
        return new UserDetailServiceImpl(userPersistencePort, userDetailUtil);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.
                csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {
                    http.requestMatchers(HttpMethod.POST, "api/auth/**").permitAll();
                    http.requestMatchers(HttpMethod.POST, "api/user/warehouse").hasRole("ADMINISTRATOR");
                    http.anyRequest().denyAll();
                })
                .addFilterBefore(new JwtValidatorFilter(javaJwtConfig), BasicAuthenticationFilter.class);


        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationStrategy(UserDetailServiceImpl userDetailsService){
        DaoAuthenticationProvider authStrategy = new DaoAuthenticationProvider();

        authStrategy.setPasswordEncoder(encoderAdapter.getPasswordEncoder());

        authStrategy.setUserDetailsService(userDetailsService);

        return authStrategy;
    }



}