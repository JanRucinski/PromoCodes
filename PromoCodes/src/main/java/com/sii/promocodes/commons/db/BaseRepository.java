package com.sii.promocodes.commons.db;

import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.NoSuchElementException;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends CrudRepository<T, ID> {

    String getEntityName();

    default T loadById(@NonNull ID id) {
        return findById(id).orElseThrow(() -> throwNoSuchElementException("id", id));
    }

    default NoSuchElementException throwNoSuchElementException(@NonNull String idName, Object propertyValue) {
        return new NoSuchElementException(getEntityName() + " with " + idName + ":" + propertyValue + " not found");
    }

    @Override
    List<T> findAll();
}