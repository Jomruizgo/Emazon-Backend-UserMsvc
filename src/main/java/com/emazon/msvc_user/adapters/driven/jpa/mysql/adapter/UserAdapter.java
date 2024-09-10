package com.emazon.msvc_user.adapters.driven.jpa.mysql.adapter;

import com.emazon.msvc_user.adapters.driven.jpa.mysql.mapper.IUserEntityMapper;
import com.emazon.msvc_user.adapters.driven.jpa.mysql.repository.IUserRepository;
import com.emazon.msvc_user.domain.model.User;
import com.emazon.msvc_user.domain.spi.IUserPersistencePort;

public class UserAdapter implements IUserPersistencePort {
    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    public UserAdapter(IUserRepository userRepository, IUserEntityMapper userEntityMapper) {
        this.userRepository = userRepository;
        this.userEntityMapper = userEntityMapper;
    }

    @Override
    public void save(User user) {
        userRepository.save(userEntityMapper.toEntity(user));
    }

    @Override
    public User findUserByEmail(String email) {
        return userEntityMapper.toModel(userRepository.findByEmail(email).orElse(null));
    }

    @Override
    public User findUserByDocumentId(Long documentId) {
        return userEntityMapper.toModel(userRepository.findByDocumentId(documentId).orElse(null));
    }
}
