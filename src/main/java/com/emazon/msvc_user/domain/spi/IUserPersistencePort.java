package com.emazon.msvc_user.domain.spi;

import com.emazon.msvc_user.domain.model.User;

public interface IUserPersistencePort {
    void save(User user);

    User findUserByEmail(String email);
    User findUserByDocumentId(Long documentId);
}
