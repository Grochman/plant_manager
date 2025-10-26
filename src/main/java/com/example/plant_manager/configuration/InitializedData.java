package com.example.plant_manager.configuration;

import com.example.plant_manager.plant.entity.Plant;
import com.example.plant_manager.plant.service.PlantService;
import com.example.plant_manager.species.entity.Species;
import com.example.plant_manager.species.service.SpeciesService;
import com.example.plant_manager.user.entity.User;
import com.example.plant_manager.user.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.control.RequestContextController;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@ApplicationScoped
public class InitializedData {
    private final UserService userService;
    private final RequestContextController requestContextController;
    private final PlantService plantService;
    private final SpeciesService speciesService;
    private final ServletContext context;
    @Inject
    public InitializedData(
            UserService userService,
            RequestContextController requestContextController,
            PlantService plantService, SpeciesService speciesService, ServletContext servletContext) {
        this.userService = userService;
        this.requestContextController = requestContextController;
        this.speciesService = speciesService;
        this.plantService = plantService;
        this.context = servletContext;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    @SneakyThrows
    private void init() {
        requestContextController.activate();

        String avatarDir = context.getInitParameter("avatarDir");

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

        Species ficus = Species.builder()
                .id(UUID.randomUUID())
                .fullName("Ficus lyrata")
                .commonName("Fiddle Leaf Fig")
                .family("Moraceae")
                .description("A popular indoor plant with large, glossy leaves.")
                .wateringRateInDays(7)
                .lightType(Species.LightType.MEDIUM)
                .origin("Western Africa")
                .build();

        speciesService.create(ficus);

        Plant kevinFicus = Plant.builder()
                .id(UUID.randomUUID())
                .name("Charlie")
                .lastWateringDate(LocalDate.of(2025, 10, 10))
                .description("A thriving Monstera kept near the living room window.")
                .age(2)
                .owner(kevin)
                .species(ficus)
                .build();

        Plant testPlant = Plant.builder()
                .id(UUID.randomUUID())
                .name("Test")
                .lastWateringDate(LocalDate.of(2025, 10, 10))
                .description("A thriving Monstera kept near the living room window.")
                .age(2)
                .owner(kevin)
                .species(ficus)
                .build();

        plantService.create(kevinFicus);
        plantService.create(testPlant);
        plantService.delete(testPlant.getId());

        requestContextController.deactivate();
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
