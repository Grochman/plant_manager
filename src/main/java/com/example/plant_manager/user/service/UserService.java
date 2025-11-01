package com.example.plant_manager.user.service;

import com.example.plant_manager.user.entity.User;
import com.example.plant_manager.user.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class UserService {
    private final UserRepository userRepository;

    @Inject
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> find(UUID id) {
        return userRepository.find(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public void create(User user) {
        userRepository.create(user);
    }

    @Transactional
    public void update(User user) {userRepository.update(user);}

    @Transactional
    public void delete(UUID id) {userRepository.delete(id);}
}
