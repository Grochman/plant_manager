package com.example.plant_manager.configuration;

import com.example.plant_manager.user.entity.User;
import com.example.plant_manager.user.service.UserService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@WebListener
public class InitializedData implements ServletContextListener {
    private UserService userService;
    private String avatarDir;
    @Override
    public void contextInitialized(ServletContextEvent event) {
        userService = (UserService) event.getServletContext().getAttribute("userService");
        avatarDir = event.getServletContext().getInitParameter("avatarDir");
        init();
    }

    @SneakyThrows
    private void init() {
        User alvin = User.builder()
                .id(UUID.fromString("c4804e0f-769e-4ab9-9ebe-0578fb4f00a6"))
                .name("Alvin")
                .avatar(loadBytes(avatarDir + "1.jpg"))
                .build();

        User cyryl = User.builder()
                .id(UUID.fromString("a4804e0f-769e-4ab9-9ebe-0578fb4f00a6"))
                .name("Cyryl")
                .avatar(loadBytes(avatarDir + "1.jpg"))
                .build();

        User kevin = User.builder()
                .id(UUID.fromString("81e1c2a9-7f57-439b-b53d-6db88b071e4e"))
                .name("Kevin")
                .avatar(loadBytes(avatarDir + "2.JPG"))
                .build();

        User barbados = User.builder()
                .id(UUID.fromString("ed6cfb2a-cad7-47dd-9b56-9d1e3c7a4197"))
                .name("Barbados")
                .build();

        userService.create(alvin);
        userService.create(kevin);
        userService.create(barbados);
        userService.create(cyryl);
    }

    @SneakyThrows
    private byte[] loadBytes(String location) {
        Path path = Paths.get(location);
        if (Files.exists(path)) {
            return Files.readAllBytes(path);
        }
        throw new IllegalStateException("Resource not found: " + location);
    }
}
