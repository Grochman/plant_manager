package com.example.plant_manager.component;

import com.example.plant_manager.user.dto.functions.UserToResponseFunction;
import com.example.plant_manager.user.dto.functions.UsersToResponseFunction;

public class DtoFunctionFactory {
    public UserToResponseFunction userToResponse() {
        return new UserToResponseFunction();
    }

    public UsersToResponseFunction usersToResponse() {
        return new UsersToResponseFunction();
    }
}
