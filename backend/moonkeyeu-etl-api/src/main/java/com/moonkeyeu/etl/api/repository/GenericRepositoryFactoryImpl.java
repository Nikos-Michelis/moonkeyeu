package com.moonkeyeu.etl.api.repository;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Scope("prototype")
public class GenericRepositoryFactoryImpl<T, ID> implements GenericRepository<T, ID> {

    private final EntityManager entityManager;
    private final Class<T> entityClass;

    @Autowired
    public GenericRepositoryFactoryImpl(EntityManager entityManager, Class<T> entityClass) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
    }

    @Override
    @Transactional
    public T save(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public void saveAll(List<T> entities) {
        final int batchSize = 500;
        int totalEntities = entities.size();
        for (int i = 0; i < totalEntities; i += batchSize) {
            List<T> batch = entities.subList(i, Math.min(i + batchSize, totalEntities));
            batch.forEach(entityManager::merge);
            entityManager.flush();
            entityManager.clear();
        }
    }

    @Override
    public T findById(ID id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    public List<T> findAll() {
        return entityManager.createQuery("from " + entityClass.getName(), entityClass).getResultList();
    }
}
