package com.example.plant_manager.plant.service;

import com.example.plant_manager.plant.entity.Plant;
import com.example.plant_manager.plant.repository.PlantRepository;
import com.example.plant_manager.species.entity.Species;
import com.example.plant_manager.species.repository.SpeciesRepository;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
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
        Species s = speciesRepository.find(plant.getSpecies().getId()).orElse(null);
        s.getPlantList().add(plant);
    }

    public void update(Plant plant) {plantRepository.update(plant);}

    public void delete(UUID id) {
        Plant plant = plantRepository.find(id).orElse(null);
        if(plant != null) {
            Species species = plant.getSpecies();
            if(species != null) {
                species.getPlantList().remove(plant);
            };
            plantRepository.delete(id);
        }
    }
}
