package com.emazon.msvc_user.configuration;

import com.emazon.msvc_user.adapters.driven.encoder.EncoderAdapter;
import com.emazon.msvc_user.adapters.driven.jpa.mysql.adapter.RoleAdapter;
import com.emazon.msvc_user.adapters.driven.jpa.mysql.adapter.UserAdapter;
import com.emazon.msvc_user.adapters.driven.jpa.mysql.mapper.IRoleEntityMapper;
import com.emazon.msvc_user.adapters.driven.jpa.mysql.mapper.IUserEntityMapper;
import com.emazon.msvc_user.adapters.driven.jpa.mysql.repository.IRoleRepository;
import com.emazon.msvc_user.adapters.driven.jpa.mysql.repository.IUserRepository;
import com.emazon.msvc_user.domain.api.IUserServicePort;
import com.emazon.msvc_user.domain.api.usecase.UserUseCase;
import com.emazon.msvc_user.domain.spi.IPasswordEncoderPort;
import com.emazon.msvc_user.domain.spi.IRolePersistencePort;
import com.emazon.msvc_user.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;

    private final IUserEntityMapper userEntityMapper;
    private final IRoleEntityMapper roleEntityMapper;

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserAdapter(userRepository, userEntityMapper);
    }

    @Bean
    IPasswordEncoderPort passwordEncoderPort(){ return new EncoderAdapter();}

    @Bean
    IRolePersistencePort rolePersistencePort(){ return new RoleAdapter(roleRepository, roleEntityMapper);}
    @Bean
    public IUserServicePort userServicePort() {
        return new UserUseCase(userPersistencePort(), rolePersistencePort(), passwordEncoderPort());
    }

}
