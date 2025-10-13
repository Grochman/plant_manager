package com.example.plant_manager.user.dto.functions;

import com.example.plant_manager.user.dto.GetUserResponse;
import com.example.plant_manager.user.entity.User;

import java.util.function.Function;

public class UserToResponseFunction implements Function<User, GetUserResponse> {
    @Override
    public GetUserResponse apply(User entity) {
        return GetUserResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
