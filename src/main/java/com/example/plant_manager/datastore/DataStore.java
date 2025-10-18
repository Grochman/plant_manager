package com.example.plant_manager.datastore;

import com.example.plant_manager.serialization.CloningUtility;
import com.example.plant_manager.user.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Log
@ApplicationScoped
@NoArgsConstructor(force = true)
public class DataStore {

    private final Set<User> users = new HashSet<>();

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

    public List<User> findAllUsers() {
        return users.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public void createUser(User entity)  throws IllegalArgumentException {
        System.out.println(entity);
        if (users.stream().anyMatch(user -> user.getId().equals(entity.getId()))) {
            throw new IllegalArgumentException("The character id \"%s\" is not unique".formatted(entity.getId()));
        }
        User newEntity = cloneWithRelationships(entity);
        users.add(newEntity);
    }

    public void updateUser(User entity)  throws IllegalArgumentException {
        User newEntity = cloneWithRelationships(entity);
        if (users.removeIf(user -> user.getId().equals(entity.getId()))) {
            users.add(newEntity);
        } else {
            throw new IllegalArgumentException("The user with id \"%s\" does not have a avatar".formatted(entity.getId()));
        }
    }

    private User cloneWithRelationships(User value) {
        User entity = cloningUtility.clone(value);
/*
        if (entity.getUser() != null) {
            entity.setUser(users.stream()
                    .filter(user -> user.getId().equals(value.getUser().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No user with id \"%s\".".formatted(value.getUser().getId()))));
        }
*/
        return entity;
    }
}
