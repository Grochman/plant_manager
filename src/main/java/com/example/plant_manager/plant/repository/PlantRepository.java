package com.example.plant_manager.plant.repository;

import com.example.plant_manager.plant.entity.Plant;
import com.example.plant_manager.species.entity.Species;
import com.example.plant_manager.user.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Dependent
@NoArgsConstructor(force = true)
public class PlantRepository {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {this.em = em;}

    public Optional<Plant> find(UUID id) {
        return  Optional.ofNullable(em.find(Plant.class, id));
    }

    public Optional<Plant> findByIdAndUser(UUID id, String login) {
        try {
            Plant plant = em.createQuery(
                            "SELECT p FROM Plant p WHERE p.id = :id AND p.owner.login = :login", Plant.class)
                    .setParameter("id", id)
                    .setParameter("login", login)
                    .getSingleResult();
            return Optional.of(plant);
        } catch (jakarta.persistence.NoResultException e) {
            return Optional.empty();
        }
    }

    public List<Plant> findAll() {
        return em.createQuery("select p from Plant p", Plant.class).getResultList();
    }

    public List<Plant> findAllByUser(User user) {
        return em.createQuery("select p from Plant p where p.owner = :user", Plant.class)
                .setParameter("user", user)
                .getResultList();
    }

    public List<Plant> find() {
        return em.createQuery("select p from Plant p", Plant.class).getResultList();
    }

    public void create(Plant entity)  throws IllegalArgumentException {
        em.persist(entity);
    }

    public void update(Plant entity)  throws IllegalArgumentException {
        em.merge(entity);
    }

    public void delete(UUID id) {
        em.remove(em.find(Plant.class, id));
    }

    public List<Plant> findAllBySpecies(UUID id) {
        return em.createQuery(
                        "SELECT p FROM Plant p WHERE p.species.id = :speciesId", Plant.class)
                .setParameter("speciesId", id)
                .getResultList();
    }

    public List<Plant> findAllBySpeciesAndUser(UUID speciesId, String login) {
        return em.createQuery(
                        "SELECT p FROM Plant p WHERE p.species.id = :speciesId AND p.owner.login = :login", Plant.class)
                .setParameter("speciesId", speciesId)
                .setParameter("login", login)
                .getResultList();
    }

    public Optional<Plant> findByIdAndSpecies(UUID id, UUID speciesId) {
        try {
            Plant plant = em.createQuery(
                            "SELECT p FROM Plant p WHERE p.species.id = :speciesId AND p.id = :id", Plant.class)
                    .setParameter("speciesId", speciesId)
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.of(plant);
        } catch (jakarta.persistence.NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<Plant> findByIdAndSpeciesAndUser(UUID id, UUID speciesId,  String login) {
        try {
            Plant plant = em.createQuery(
                            "SELECT p FROM Plant p WHERE p.species.id = :speciesId AND p.id = :id AND p.owner.login = :login", Plant.class)
                    .setParameter("speciesId", speciesId)
                    .setParameter("id", id)
                    .setParameter("login", login)
                    .getSingleResult();
            return Optional.of(plant);
        } catch (jakarta.persistence.NoResultException e) {
            return Optional.empty();
        }
    }
}
