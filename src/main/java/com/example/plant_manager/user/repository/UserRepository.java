package com.example.plant_manager.user.repository;

import com.example.plant_manager.user.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.NoArgsConstructor;

import java.io.*;
import java.util.*;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class UserRepository {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {this.em = em;}

    public Optional<User> find(UUID id) {
        return  Optional.ofNullable(em.find(User.class, id));
    }

    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    public void create(User entity)  throws IllegalArgumentException {
        em.persist(entity);
    }

    public void update(User entity)  throws IllegalArgumentException {
        em.merge(entity);
    }

    public void delete(UUID id) {
        em.remove(em.find(Character.class, id));
    }
}
