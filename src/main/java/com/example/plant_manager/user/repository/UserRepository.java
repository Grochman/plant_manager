package com.example.plant_manager.user.repository;

import com.example.plant_manager.datastore.DataStore;
import com.example.plant_manager.user.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

import java.io.*;
import java.util.*;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class UserRepository {
    private final DataStore dataStore;

    @Inject
    public UserRepository(DataStore dataStore) {this.dataStore = dataStore;}

    public Optional<User> find(UUID id) {
        return dataStore.findUser(id);
    }

    public List<User> findAll() {
        return dataStore.findAllUsers();
    }

    public void create(User entity)  throws IllegalArgumentException {
        dataStore.createUser(entity);
    }

    public void update(User entity)  throws IllegalArgumentException {
        dataStore.updateUser(entity);
    }

    public void delete(UUID id) {
        dataStore.deleteUser(id);
    }
}
