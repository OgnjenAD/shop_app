package com.dailycodework.dream_shops.service.user;

import com.dailycodework.dream_shops.dto.UserDto;
import com.dailycodework.dream_shops.model.Users;
import com.dailycodework.dream_shops.request.user.CreateUserRequest;
import com.dailycodework.dream_shops.request.user.UserUpdateRequest;

public interface IUserService {

    Users getUserById(Long userId);
    Users createUser(CreateUserRequest request);
    Users updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(Users user);

    Users getAuthenticatedUser();
}
