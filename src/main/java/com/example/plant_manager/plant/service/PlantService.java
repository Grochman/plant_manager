package com.example.plant_manager.plant.service;

import com.example.plant_manager.plant.entity.Plant;
import com.example.plant_manager.plant.repository.PlantRepository;
import com.example.plant_manager.species.entity.Species;
import com.example.plant_manager.species.repository.SpeciesRepository;
import com.example.plant_manager.user.entity.User;
import com.example.plant_manager.user.repository.UserRepository;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
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
    private final UserRepository userRepository;
    private final SecurityContext securityContext;

    @Inject
    public PlantService(PlantRepository plantRepository, SpeciesRepository speciesRepository, SecurityContext securityContext, UserRepository userRepository) {
        this.plantRepository = plantRepository;
        this.speciesRepository = speciesRepository;
        this.securityContext = securityContext;
        this.userRepository = userRepository;
    }

    public Optional<Plant> find(UUID id) {
        if(securityContext.isCallerInRole("admin")){
            return plantRepository.find(id);
        }
        else{
            return plantRepository.findByIdAndUser(id, securityContext.getCallerPrincipal().getName());
        }
    }

    public List<Plant> findAll() {
        return plantRepository.findAll();
    }

    public Optional<List<Plant>> findAllBySpecies(UUID id) {
        Species s = speciesRepository.find(id).orElse(null);
        if (s == null) {
            return Optional.empty();
        }
        if(securityContext.isCallerInRole("admin")){
            return Optional.of(plantRepository.findAllBySpecies(s.getId()));
        }
        else{
            return Optional.of(plantRepository.findAllBySpeciesAndUser(s.getId(), securityContext.getCallerPrincipal().getName()));
        }
    }

    public Optional<Plant> findByIdAndSpecies(UUID id, UUID speciesId) {
        Species s = speciesRepository.find(speciesId).orElse(null);
        if (s == null) {
            return Optional.empty();
        }

        if(securityContext.isCallerInRole("admin")){
            return plantRepository.findByIdAndSpecies(id, speciesId);
        }
        else{
            System.out.println(securityContext.getCallerPrincipal().getName());
            return plantRepository.findByIdAndSpeciesAndUser(id,speciesId, securityContext.getCallerPrincipal().getName());
        }
    }

    public void create(Plant plant) {
        User owner = userRepository.findByLogin(securityContext.getCallerPrincipal().getName()).orElse(null);
        if (owner == null) {return;}

        plant.setOwner(owner);
        plantRepository.create(plant);
        Species s = speciesRepository.find(plant.getSpecies().getId()).orElse(null);
        s.getPlantList().add(plant);
    }

    public void update(Plant plant) {
        Plant p = find(plant.getId()).orElse(null);
        if(p != null){
            plantRepository.update(plant);
        }
    }

    public void delete(UUID id) {
        Plant plant = find(id).orElse(null);
        if(plant != null) {
            Species species = plant.getSpecies();
            if(species != null) {
                species.getPlantList().remove(plant);
            };
            plantRepository.delete(id);
        }
    }
}
