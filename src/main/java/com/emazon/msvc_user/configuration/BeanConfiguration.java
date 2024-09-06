package com.emazon.msvc_user.configuration;

import com.emazon.msvc_user.adapters.driven.jpa.mysql.adapter.UserAdapter;
import com.emazon.msvc_user.domain.api.IUserServicePort;
import com.emazon.msvc_user.domain.api.usecase.UserUseCase;
import com.emazon.msvc_user.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserAdapter();
    }

    @Bean
    public IUserServicePort userServicePort() {
        return new UserUseCase(userPersistencePort());
    }
}
