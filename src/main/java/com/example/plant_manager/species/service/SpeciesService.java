package com.example.plant_manager.species.service;

import com.example.plant_manager.species.entity.Species;
import com.example.plant_manager.species.repository.SpeciesRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class SpeciesService {
    private final SpeciesRepository speciesRepository;

    @Inject
    public SpeciesService(SpeciesRepository speciesRepository) {
        this.speciesRepository = speciesRepository;
    }

    public Optional<Species> find(UUID id) {
        return speciesRepository.find(id);
    }

    public List<Species> findAll() {
        return speciesRepository.findAll();
    }

    public void create(Species species) {
        speciesRepository.create(species);
    }

    public void update(Species species) {speciesRepository.update(species);}

    public void delete(UUID id) {speciesRepository.delete(id);}
}
