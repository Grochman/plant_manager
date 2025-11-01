package com.example.plant_manager.plant.repository;

import com.example.plant_manager.plant.entity.Plant;
import com.example.plant_manager.species.entity.Species;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class PlantRepository {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {this.em = em;}

    public Optional<Plant> find(UUID id) {
        return  Optional.ofNullable(em.find(Plant.class, id));
    }

    public List<Plant> findAll() {
        return em.createQuery("select p from Plant p", Plant.class).getResultList();
    }

    public void create(Plant entity)  throws IllegalArgumentException {
        em.persist(entity);
    }

    public void update(Plant entity)  throws IllegalArgumentException {
        em.merge(entity);
    }

    public void delete(UUID id) {em.remove(em.find(Plant.class, id));}

    public List<Plant> findAllBySpecies(Species species) {
        return findAll().stream()
                .filter(plant -> species.getId().equals(plant.getSpecies().getId()))
                .collect(Collectors.toList());
    }

    public Optional<Plant> findByIdAndSpecies(UUID id, Species species) {
        return findAll().stream()
                .filter(plant -> species.getId().equals(plant.getSpecies().getId()))
                .filter(plant -> plant.getId().equals(id))
                .findFirst();
    }
}
