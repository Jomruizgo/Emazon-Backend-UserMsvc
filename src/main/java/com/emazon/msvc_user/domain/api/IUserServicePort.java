package com.emazon.msvc_user.domain.api;

import com.emazon.msvc_user.domain.model.User;

public interface IUserServicePort {
    void saveWarehouseAssistant(User user);

    void saveClient(User user);

}
