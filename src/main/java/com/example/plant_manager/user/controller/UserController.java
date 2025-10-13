package com.example.plant_manager.user.controller;

import com.example.plant_manager.component.DtoFunctionFactory;
import com.example.plant_manager.servlet.exceptions.BadRequestException;
import com.example.plant_manager.user.dto.GetUserResponse;
import com.example.plant_manager.user.dto.GetUsersResponse;
import com.example.plant_manager.user.entity.User;
import com.example.plant_manager.user.service.UserService;
import com.example.plant_manager.servlet.exceptions.NotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class UserController {
    private final UserService service;

    private final DtoFunctionFactory factory;

    public UserController(UserService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public GetUsersResponse getUsers() {
        return factory.usersToResponse().apply(service.findAll());
    }

    public GetUserResponse getUser(UUID uuid) {
        return service.find(uuid)
                .map(factory.userToResponse())
                .orElseThrow(NotFoundException::new);
    }

    public byte[] getUserAvatar(UUID uuid){
        return service.find(uuid)
                .map(User::getAvatar)
                .orElseThrow(NotFoundException::new);
    }

    public void deleteUserAvatar(UUID uuid) {
        User user = service.find(uuid)
                .map(u -> {
                    if (u.getAvatar() == null) {
                        throw new NotFoundException();
                    }
                    u.setAvatar(null);
                    return u;
                })
                .orElseThrow(NotFoundException::new);

        service.update(user);
    }

    public void putUserAvatar(UUID uuid, InputStream avatar) {
        User user = service.find(uuid)
                .map(u -> {
                    if (u.getAvatar() != null) {
                        throw new BadRequestException("Avatar already exists");
                    }
                    try {
                        byte[] bytes = avatar.readAllBytes();

                        Path avatarsDir = Paths.get("src/main/resources/avatars");
                        Files.createDirectories(avatarsDir);

                        Path filePath = avatarsDir.resolve(uuid + ".png");

                        Files.write(filePath, bytes);

                        u.setAvatar(bytes);
                        return u;
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to save avatar", e);
                    }
                })
                .orElseThrow(NotFoundException::new);

        service.update(user);
    }

    public void patchUserAvatar(UUID uuid, InputStream avatar) {
        User user = service.find(uuid)
                .map(u -> {
                    if (u.getAvatar() == null) {
                        throw new NotFoundException();
                    }
                    try {
                        byte[] bytes = avatar.readAllBytes();

                        Path avatarsDir = Paths.get("src/main/resources/avatars");
                        Files.createDirectories(avatarsDir);

                        Path filePath = avatarsDir.resolve(uuid + ".png");

                        Files.write(filePath, bytes);

                        u.setAvatar(bytes);
                        return u;
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to save avatar", e);
                    }
                })
                .orElseThrow(NotFoundException::new);

        service.update(user);
    }
}
