package com.moonkeyeu.etl.api.repository;

import java.util.List;

public interface GenericRepository<T, ID> {
    T save(T entity);
    void saveAll(List<T> entities);
    T findById(ID id);
    List<T> findAll();
}

