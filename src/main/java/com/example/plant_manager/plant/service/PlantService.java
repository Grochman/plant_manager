package com.example.plant_manager.plant.service;

import com.example.plant_manager.component.LogOperation;
import com.example.plant_manager.plant.entity.Plant;
import com.example.plant_manager.plant.repository.PlantRepository;
import com.example.plant_manager.species.entity.Species;
import com.example.plant_manager.species.repository.SpeciesRepository;
import com.example.plant_manager.user.entity.User;
import com.example.plant_manager.user.entity.UserRoles;
import com.example.plant_manager.user.repository.UserRepository;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.NotFoundException;
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
        Optional<Plant> plant = plantRepository.find(id);

        if (plant.isEmpty()) {
            return Optional.empty();
        }

        if (securityContext.isCallerInRole("admin")) {
            return plant;
        }

        String username = getAuthenticatedUsername();
        if (!plant.get().getOwner().getLogin().equals(username)) {
            throw new ForbiddenException("Access denied");
        }

        return plant;
    }

    public List<Plant> findAll() {
        return plantRepository.findAll();
    }

    public List<Plant> findAll(User user) {
        return plantRepository.findAllByUser(user);
    }

    @RolesAllowed(UserRoles.USER)
    public List<Plant> findAllForCallerPrincipal() {
        System.out.println("GOWNO GOWNO");
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return findAll();
        }
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        return findAll(user);
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

        String username = getAuthenticatedUsername();

        if (username == null) {
            throw new NotAuthorizedException("User is not authenticated");
        }

        if (securityContext.isCallerInRole("admin")) {
            return plantRepository.findByIdAndSpecies(id, speciesId);
        }

        Optional<Plant> plant = plantRepository.findByIdAndSpecies(id, speciesId);

        if (plant.isPresent() && !plant.get().getOwner().getLogin().equals(username)) {
            throw new ForbiddenException("User cannot access this plant");
        }

        return plantRepository.findByIdAndSpeciesAndUser(id, speciesId, username);
    }

    @LogOperation
    public void create(Plant plant) {
        if (securityContext.getCallerPrincipal() == null) {
            throw new NotAuthorizedException("User is not authenticated");
        }

        String username = securityContext.getCallerPrincipal().getName();

        User owner = userRepository.findByLogin(username)
                .orElseThrow(() -> new NotAuthorizedException("User not found in system"));

        Species species = speciesRepository.find(plant.getSpecies().getId())
                .orElseThrow(() -> new NotFoundException("species not found"));

        plant.setOwner(owner);
        plantRepository.create(plant);

        species.getPlantList().add(plant);
    }

    @LogOperation
    public void update(Plant plant) {
        String username = getAuthenticatedUsername();

        Plant existing = find(plant.getId())
                .orElseThrow(() -> new NotFoundException("plant not found"));

        if (!securityContext.isCallerInRole("admin")) {
            if (!existing.getOwner().getLogin().equals(username)) {
                throw new ForbiddenException("You cannot modify this plant");
            }
        }

        plantRepository.update(plant);
    }

    @LogOperation
    public void delete(UUID id) {
        String username = getAuthenticatedUsername();

        Plant existing = find(id)
                .orElseThrow(() -> new NotFoundException("plant not found"));

        if (!securityContext.isCallerInRole("admin")) {
            if (!existing.getOwner().getLogin().equals(username)) {
                throw new ForbiddenException("You cannot delete this plant");
            }
        }

        Species species = existing.getSpecies();
        if (species != null) {
            species.getPlantList().remove(existing);
        }

        plantRepository.delete(id);
    }

    private String getAuthenticatedUsername() {
        if (securityContext.getCallerPrincipal() == null) {
            throw new NotAuthorizedException("User is not authenticated");
        }
        return securityContext.getCallerPrincipal().getName();
    }
}
