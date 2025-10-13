package com.example.plant_manager.user.repository;

import com.example.plant_manager.serialization.CloningUtility;
import com.example.plant_manager.user.entity.User;
import lombok.SneakyThrows;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class UserRepository {
    private final Set<User> users = new HashSet<>();
    private final CloningUtility cloningUtility;
    public UserRepository(CloningUtility cloningUtility) {this.cloningUtility = cloningUtility;}

    public Optional<User> find(UUID id) {
        return findAll().stream()
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

    public List<User> findAll() {
        return users.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public void create(User entity)  throws IllegalArgumentException {
        if (users.stream().anyMatch(user -> user.getId().equals(entity.getId()))) {
            throw new IllegalArgumentException("The character id \"%s\" is not unique".formatted(entity.getId()));
        }
        User newEntity = cloneWithRelationships(entity);
        users.add(newEntity);
    }

    public void update(User entity)  throws IllegalArgumentException {
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
