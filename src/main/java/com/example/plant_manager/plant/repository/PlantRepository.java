package com.example.plant_manager.plant.repository;

import com.example.plant_manager.plant.entity.Plant;
import com.example.plant_manager.plant.entity.Plant_;
import com.example.plant_manager.species.entity.Species_;
import com.example.plant_manager.user.entity.User;
import com.example.plant_manager.user.entity.User_;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
@NoArgsConstructor(force = true)
public class PlantRepository {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {this.em = em;}

    public Optional<Plant> find(UUID id) {
        return Optional.ofNullable(em.find(Plant.class, id));
    }

    public Optional<Plant> findByIdAndUser(UUID id, String login) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Plant> cq = cb.createQuery(Plant.class);
        Root<Plant> plant = cq.from(Plant.class);

        cq.select(plant)
                .where(cb.and(
                        cb.equal(plant.get(Plant_.id), id),
                        cb.equal(plant.get(Plant_.owner).get(User_.login), login)
                ));

        try {
            return Optional.of(em.createQuery(cq).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<Plant> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Plant> cq = cb.createQuery(Plant.class);
        Root<Plant> plant = cq.from(Plant.class);

        cq.select(plant);

        return em.createQuery(cq).getResultList();
    }

    public List<Plant> findAllByUser(User user) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Plant> cq = cb.createQuery(Plant.class);
        Root<Plant> plant = cq.from(Plant.class);

        cq.select(plant)
                .where(cb.equal(plant.get(Plant_.owner), user));

        return em.createQuery(cq).getResultList();
    }

    public List<Plant> find() {
        return findAll();
    }

    public void create(Plant entity) throws IllegalArgumentException {
        em.persist(entity);
    }

    public void update(Plant entity) throws IllegalArgumentException {
        em.merge(entity);
    }

    public void delete(UUID id) {
        Plant plant = em.find(Plant.class, id);
        if (plant != null) {
            em.remove(plant);
        }
    }

    public List<Plant> findAllBySpecies(UUID id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Plant> cq = cb.createQuery(Plant.class);
        Root<Plant> plant = cq.from(Plant.class);

        cq.select(plant)
                .where(cb.equal(plant.get(Plant_.species).get(Species_.id), id));

        return em.createQuery(cq).getResultList();
    }

    public List<Plant> findAllBySpeciesAndUser(UUID speciesId, String login) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Plant> cq = cb.createQuery(Plant.class);
        Root<Plant> plant = cq.from(Plant.class);

        cq.select(plant)
                .where(cb.and(
                        cb.equal(plant.get(Plant_.species).get(Species_.id), speciesId),
                        cb.equal(plant.get(Plant_.owner).get(User_.login), login)
                ));

        return em.createQuery(cq).getResultList();
    }

    public Optional<Plant> findByIdAndSpecies(UUID id, UUID speciesId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Plant> cq = cb.createQuery(Plant.class);
        Root<Plant> plant = cq.from(Plant.class);

        cq.select(plant)
                .where(cb.and(
                        cb.equal(plant.get(Plant_.species).get(Species_.id), speciesId),
                        cb.equal(plant.get(Plant_.id), id)
                ));

        try {
            return Optional.of(em.createQuery(cq).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<Plant> findByIdAndSpeciesAndUser(UUID id, UUID speciesId, String login) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Plant> cq = cb.createQuery(Plant.class);
        Root<Plant> plant = cq.from(Plant.class);

        cq.select(plant)
                .where(cb.and(
                        cb.equal(plant.get(Plant_.species).get(Species_.id), speciesId),
                        cb.equal(plant.get(Plant_.id), id),
                        cb.equal(plant.get(Plant_.owner).get(User_.login), login)
                ));

        try {
            return Optional.of(em.createQuery(cq).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}