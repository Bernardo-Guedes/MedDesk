package com.example.MedDesk.repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class RepositorioEmMemoria<T, ID> {

    protected final Map<ID, T> dados = new LinkedHashMap<>();

    protected abstract ID obterId(T entidade);

    public T save(T entidade) {
        dados.put(obterId(entidade), entidade);
        return entidade;
    }

    public Optional<T> findById(ID id) {
        return Optional.ofNullable(dados.get(id));
    }

    public List<T> findAll() {
        return new ArrayList<>(dados.values());
    }

    public void deleteById(ID id) {
        dados.remove(id);
    }

    public boolean existsById(ID id) {
        return dados.containsKey(id);
    }
}