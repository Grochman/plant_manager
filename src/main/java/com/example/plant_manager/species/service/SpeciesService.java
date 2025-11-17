package com.example.plant_manager.species.service;

import com.example.plant_manager.species.entity.Species;
import com.example.plant_manager.species.repository.SpeciesRepository;
import com.example.plant_manager.user.entity.UserRoles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Local;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class SpeciesService {
    private final SpeciesRepository speciesRepository;

    @Inject
    public SpeciesService(SpeciesRepository speciesRepository) {
        this.speciesRepository = speciesRepository;
    }

    @RolesAllowed(UserRoles.USER)
    public Optional<Species> find(UUID id) {
        return speciesRepository.find(id);
    }

    @RolesAllowed(UserRoles.USER)
    public List<Species> findAll() {
        return speciesRepository.findAll();
    }

    @RolesAllowed(UserRoles.ADMIN)
    public void create(Species species) {
        speciesRepository.create(species);
    }

    @RolesAllowed(UserRoles.ADMIN)
    public void update(Species species) {speciesRepository.update(species);}

    @RolesAllowed(UserRoles.ADMIN)
    public void delete(UUID id) {speciesRepository.delete(id);}
}
