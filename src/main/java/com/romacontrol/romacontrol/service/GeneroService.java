package com.romacontrol.romacontrol.service;

import java.util.List;
import java.util.Optional;

import com.romacontrol.romacontrol.model.Genero;

public interface GeneroService {
    Genero guardar(Genero genero);
    List<Genero> obtenerTodos();
    Optional<Genero> obtenerPorId(Long id);
    void eliminar(Long id);
}
