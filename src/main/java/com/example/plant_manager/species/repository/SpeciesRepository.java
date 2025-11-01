package com.example.plant_manager.species.repository;

import com.example.plant_manager.species.entity.Species;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class SpeciesRepository {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {this.em = em;}

    public Optional<Species> find(UUID id) {
        return  Optional.ofNullable(em.find(Species.class, id));
    }

    public List<Species> findAll() {
        return em.createQuery("select s from Species s", Species.class).getResultList();
    }

    public void create(Species entity)  throws IllegalArgumentException {
        em.persist(entity);
    }

    public void update(Species entity)  throws IllegalArgumentException { em.merge(entity);}

    public void delete(UUID id) { em.remove(em.find(Species.class, id));}
}
