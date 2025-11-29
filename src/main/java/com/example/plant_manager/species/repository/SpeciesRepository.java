package com.example.plant_manager.species.repository;

import com.example.plant_manager.species.entity.Species;
import com.example.plant_manager.species.entity.Species_; // Metamodel
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
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
public class SpeciesRepository {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {this.em = em;}

    public Optional<Species> find(UUID id) {
        return Optional.ofNullable(em.find(Species.class, id));
    }

    public List<Species> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Species> cq = cb.createQuery(Species.class);
        Root<Species> root = cq.from(Species.class);

        cq.select(root);

        return em.createQuery(cq).getResultList();
    }

    public void create(Species entity) throws IllegalArgumentException {
        em.persist(entity);
    }

    public void update(Species entity) throws IllegalArgumentException {
        em.merge(entity);
    }

    public void delete(UUID id) {
        Species species = em.find(Species.class, id);
        if (species != null) {
            em.remove(species);
        }
    }
}