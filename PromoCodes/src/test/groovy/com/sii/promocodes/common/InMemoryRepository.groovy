package com.sii.promocodes.common

import org.springframework.data.repository.CrudRepository

class InMemoryRepository<T, ID> implements CrudRepository<T, ID> {

    Map<ID, T> store = [:]

    @Override
    <S extends T> S save(S object) {
        store[object.id as ID] = object
    }

    T save(ID id, T object) {
        store[id] = object
        return object
    }

    @Override
    <S extends T> Iterable<S> saveAll(Iterable<S> objects) {
        objects.collect { save(it) }
    }

    @Override
    Optional<T> findById(ID id) {
        Optional.ofNullable(store[id])
    }

    @Override
    boolean existsById(ID id) {
        store.containsKey(id)
    }

    @Override
    void delete(T object) {
        store.remove(object.id as ID)
    }

    @Override
    void deleteAll(Iterable<? extends T> objects) {
        objects.collect { delete(it) }
    }

    @Override
    void deleteAllById(Iterable<? extends ID> ids) {
        ids.each { id -> store.remove(id) }
    }

    @Override
    void deleteAll() {
        store.clear()
    }

    @Override
    List<T> findAll() {
        store.values().collect()
    }

    @Override
    Iterable<T> findAllById(Iterable<ID> ids) {
        store.values().findAll { ids.contains(it.id as ID) }
    }

    @Override
    long count() {
        store.size()
    }

    @Override
    void deleteById(ID id) {
        store.remove(id)
    }

}