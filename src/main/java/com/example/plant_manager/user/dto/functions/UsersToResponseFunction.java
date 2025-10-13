package com.example.plant_manager.user.dto.functions;

import com.example.plant_manager.user.dto.GetUsersResponse;
import com.example.plant_manager.user.entity.User;

import java.util.List;
import java.util.function.Function;

public class UsersToResponseFunction implements Function<List<User>, GetUsersResponse> {

    @Override
    public GetUsersResponse apply(List<User> entities) {
        return GetUsersResponse.builder()
                .users(entities.stream()
                        .map(user-> GetUsersResponse.User.builder()
                                .id(user.getId())
                                .name(user.getName())
                                .build())
                        .toList())
                .build();
    }
}
