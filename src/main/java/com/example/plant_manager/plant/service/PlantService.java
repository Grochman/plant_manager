package com.example.plant_manager.plant.service;

import com.example.plant_manager.plant.entity.Plant;
import com.example.plant_manager.plant.repository.PlantRepository;
import com.example.plant_manager.user.entity.User;
import com.example.plant_manager.user.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class PlantService {
    private final PlantRepository plantRepository;

    @Inject
    public PlantService(PlantRepository userRepository) {
        this.plantRepository = userRepository;
    }

    public Optional<Plant> find(UUID id) {
        return plantRepository.find(id);
    }

    public List<Plant> findAll() {
        return plantRepository.findAll();
    }

    public void create(Plant plant) {
        plantRepository.create(plant);
    }

    public void update(Plant plant) {plantRepository.update(plant);}

    public void delete(UUID id) {plantRepository.delete(id);}
}
