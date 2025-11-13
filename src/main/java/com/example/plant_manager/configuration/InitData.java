package com.example.plant_manager.configuration;

import com.example.plant_manager.plant.entity.Plant;
import com.example.plant_manager.plant.service.PlantService;
import com.example.plant_manager.species.entity.Species;
import com.example.plant_manager.species.service.SpeciesService;
import com.example.plant_manager.user.entity.User;
import com.example.plant_manager.user.entity.UserRoles;
import com.example.plant_manager.user.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RunAs;
import jakarta.ejb.DependsOn;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Singleton
@Startup
@NoArgsConstructor
@DependsOn("InitAdmin")
@DeclareRoles({UserRoles.ADMIN, UserRoles.USER})
@RunAs(UserRoles.ADMIN)
public class InitData {

    private UserService userService;
    private PlantService plantService;
    private SpeciesService speciesService;

    @Inject
    private SecurityContext securityContext;

    @EJB
    public void setUserService(UserService userService) {this.userService = userService;}

    @EJB
    public void setPlantService(PlantService plantService) {this.plantService = plantService;}

    @EJB
    public void setSpeciesService(SpeciesService speciesService) {this.speciesService = speciesService;}

    @Inject
    private Pbkdf2PasswordHash passwordHash;
    //@Inject
    //private ServletContext context;

    //public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {}


    @PostConstruct
    public void init() {
        System.out.println(">>> Data init called");
        //String avatarDir = context.getInitParameter("avatarDir");
        if (userService.find("admin").isEmpty()) {
            System.out.println(">>> Data init inserting");

            User admin = User.builder()
                    .id(UUID.fromString("c4804e0f-769e-4ab9-9ebe-0578fb4f00a6"))
                    .login("admin")
                    .name("System")
                    .password(passwordHash.generate("adminadmin".toCharArray()))
                    .roles(List.of(UserRoles.ADMIN, UserRoles.USER))
                    .build();

            User kevin = User.builder()
                    .id(UUID.fromString("81e1c2a9-7f57-439b-b53d-6db88b071e4e"))
                    .login("kevin")
                    .name("Kevin")
                    .password(passwordHash.generate("useruser".toCharArray()))
                    .roles(List.of(UserRoles.USER))
                    .build();

            User alice = User.builder()
                    .id(UUID.fromString("ed6cfb2a-cad7-47dd-9b56-9d1e3c7a4197"))
                    .login("alice")
                    .name("Alice")
                    .password(passwordHash.generate("useruser".toCharArray()))
                    .roles(List.of(UserRoles.USER))
                    .build();

            userService.create(admin);
            userService.create(kevin);
            userService.create(alice);

            Species ficus = Species.builder()
                    .id(UUID.fromString("83da3ba6-c7e4-4dfe-846a-64284677f45c"))
                    .fullName("Ficus lyrata")
                    .commonName("Fiddle Leaf Fig")
                    .family("Moraceae")
                    .description("A popular indoor plant with large, glossy leaves.")
                    .wateringRateInDays(7)
                    .lightType(Species.LightType.MEDIUM)
                    .origin("Western Africa")
                    .build();

            Species testSpecies = Species.builder()
                    .id(UUID.fromString("a07d4383-284f-490d-9817-cc4c362d5f59"))
                    .fullName("test")
                    .commonName("Test")
                    .family("testfamily")
                    .description("A popular indoor plant with large, glossy leaves.")
                    .wateringRateInDays(7)
                    .lightType(Species.LightType.MEDIUM)
                    .origin("Western Africa")
                    .build();


            Plant kevinFicus = Plant.builder()
                    .id(UUID.fromString("0b8c0653-b196-4ac4-8015-3e43c117517e"))
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

            Plant testPlant2 = Plant.builder()
                    .id(UUID.randomUUID())
                    .name("Test2")
                    .lastWateringDate(LocalDate.of(2025, 10, 10))
                    .description("A thriving Monstera kept near the living room window.")
                    .age(2)
                    .owner(alice)
                    .species(ficus)
                    .build();

            ficus.setPlantList(new ArrayList<>(List.of(kevinFicus, testPlant, testPlant2)));
            speciesService.create(ficus);
            speciesService.create(testSpecies);
        }
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
