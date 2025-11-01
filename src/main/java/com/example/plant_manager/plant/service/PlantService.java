package com.example.plant_manager.plant.service;

import com.example.plant_manager.plant.entity.Plant;
import com.example.plant_manager.plant.repository.PlantRepository;
import com.example.plant_manager.species.model.SpeciessModel;
import com.example.plant_manager.species.repository.SpeciesRepository;
import com.example.plant_manager.species.service.SpeciesService;
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
    private final SpeciesRepository speciesRepository;

    @Inject
    public PlantService(PlantRepository userRepository, SpeciesRepository speciesRepository) {
        this.plantRepository = userRepository;
        this.speciesRepository = speciesRepository;
    }

    public Optional<Plant> find(UUID id) {
        return plantRepository.find(id);
    }

    public List<Plant> findAll() {
        return plantRepository.findAll();
    }

    public Optional<List<Plant>> findAllBySpecies(UUID id) {
        return speciesRepository.find(id)
                .map(plantRepository::findAllBySpecies);
    }

    public Optional<Plant> findByIdAndSpecies(UUID id, UUID speciesId) {
        return speciesRepository.find(speciesId)
                .flatMap(species -> plantRepository.findByIdAndSpecies(id, species));
    }


    public void create(Plant plant) {
        plantRepository.create(plant);
    }

    public void update(Plant plant) {plantRepository.update(plant);}

    public void delete(UUID id) {plantRepository.delete(id);}
}
