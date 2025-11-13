package com.example.plant_manager.user.dto.functions;

import com.example.plant_manager.user.dto.PutUserRequest;
import com.example.plant_manager.user.entity.User;
import com.example.plant_manager.user.entity.UserRoles;

import java.util.List;
import java.util.UUID;

public class RequestToUserFunction {

    public User apply(UUID id, PutUserRequest request) {
        return User.builder()
                .id(id)
                .login(request.getLogin())
                .password(request.getPassword())
                .roles(List.of(UserRoles.USER))
                .build();
    }
}
