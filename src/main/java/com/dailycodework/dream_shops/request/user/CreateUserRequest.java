package com.dailycodework.dream_shops.request.user;

import com.dailycodework.dream_shops.model.Role;
import lombok.Data;

import java.util.Collection;
import java.util.HashSet;

@Data
public class CreateUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Collection<Role> roles = new HashSet<>();
}
