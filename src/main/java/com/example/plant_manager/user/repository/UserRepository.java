package com.example.plant_manager.user.repository;

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
public class UserRepository {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {this.em = em;}

    public Optional<User> find(UUID id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    public List<User> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);

        cq.select(root);

        return em.createQuery(cq).getResultList();
    }

    public Optional<User> findByLogin(String login) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);

        cq.select(root)
                .where(cb.equal(root.get(User_.login), login));

        try {
            return Optional.of(em.createQuery(cq).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public void create(User entity) throws IllegalArgumentException {
        em.persist(entity);
    }

    public void update(User entity) throws IllegalArgumentException {
        em.merge(entity);
    }

    public void delete(UUID id) {
        User user = em.find(User.class, id);
        if (user != null) {
            em.remove(user);
        }
    }
}