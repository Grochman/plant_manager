package com.example.plant_manager.datastore;

import com.example.plant_manager.plant.entity.Plant;
import com.example.plant_manager.serialization.CloningUtility;
import com.example.plant_manager.servlet.exceptions.NotFoundException;
import com.example.plant_manager.species.entity.Species;
import com.example.plant_manager.user.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class DataStore {

    private final Set<User> users = new HashSet<>();
    private final Set<Species> species = new HashSet<>();
    private final Set<Plant> plants = new HashSet<>();

    private final CloningUtility cloningUtility;

    @Inject
    public DataStore(CloningUtility cloningUtility) {
        this.cloningUtility = cloningUtility;
    }

    public Optional<User> findUser(UUID id) {
        return findAllUsers().stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public List<User> findAllUsers() {
        return users.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public void createUser(User entity)  throws IllegalArgumentException {
        if (users.stream().anyMatch(user -> user.getId().equals(entity.getId()))) {
            throw new IllegalArgumentException("The character id \"%s\" is not unique".formatted(entity.getId()));
        }
        User newEntity = cloningUtility.clone(entity);
        users.add(newEntity);
    }

    public void updateUser(User entity)  throws IllegalArgumentException {
        User newEntity = cloningUtility.clone(entity);
        if (users.removeIf(user -> user.getId().equals(entity.getId()))) {
            users.add(newEntity);
        } else {
            throw new IllegalArgumentException("The user with id \"%s\" does not have a avatar".formatted(entity.getId()));
        }
    }

    public void deleteUser(UUID id) {
        findUser(id).ifPresent(user -> {
           plants.removeIf(plant -> plant.getOwner().getId().equals(user.getId()));
           users.remove(user);
        });
    }

    public Optional<Species> findSpecies(UUID id) {
        return findAllSpecies().stream()
                .filter(species -> species.getId().equals(id))
                .findFirst();
    }

    public List<Species> findAllSpecies() {
        return species.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public void createSpecies(Species entity)  throws IllegalArgumentException {
        if (species.stream().anyMatch(species -> species.getId().equals(entity.getId()))) {
            throw new IllegalArgumentException("The character id \"%s\" is not unique".formatted(entity.getId()));
        }
        species.add(cloningUtility.clone(entity));
    }

    public void updateSpecies(Species entity)  throws IllegalArgumentException {
        Species newEntity = cloningUtility.clone(entity);
        if (species.removeIf(species -> species.getId().equals(entity.getId()))) {
            species.add(newEntity);
        } else {
            throw new IllegalArgumentException("The user with id \"%s\" does not have a avatar".formatted(entity.getId()));
        }
    }

    public void deleteSpecies(UUID id) {
        findSpecies(id).ifPresent(speciesItem -> {
            plants.removeIf(plant -> plant.getSpecies().getId().equals(speciesItem.getId()));
            species.remove(speciesItem);
        });
    }

    public Optional<Plant> findPlant(UUID id) {
        return findAllPlants().stream()
                .filter(plant -> plant.getId().equals(id))
                .findFirst();
    }

    public List<Plant> findAllPlants() {
        return plants.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public void createPlant(Plant entity)  throws IllegalArgumentException {
        if (plants.stream().anyMatch(plant -> plant.getId().equals(entity.getId()))) {
            throw new IllegalArgumentException("The character id \"%s\" is not unique".formatted(entity.getId()));
        }
        plants.add(cloningUtility.clone(entity));
    }

    public void updatePlant(Plant entity)  throws IllegalArgumentException {
        Plant newEntity = cloningUtility.clone(entity);
        if (plants.removeIf(plant -> plant.getId().equals(entity.getId()))) {
            plants.add(newEntity);
        } else {
            throw new IllegalArgumentException("The user with id \"%s\" does not have a avatar".formatted(entity.getId()));
        }
    }

    public void deletePlant(UUID id) {
        findPlant(id).ifPresent(plant -> {
            plants.remove(plant);
        });
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    private static <T extends Serializable> T clone(T object) {
        try (ByteArrayInputStream is = new ByteArrayInputStream(writeObject(object).toByteArray());
             ObjectInputStream ois = new ObjectInputStream(is)) {
            return (T) ois.readObject();
        }
    }

    private static <T extends Serializable> ByteArrayOutputStream writeObject(T object) throws IOException {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(object);
            return os;
        }
    }
}
