package com.romacontrol.romacontrol.service;

import java.util.List;
import java.util.Optional;

import com.romacontrol.romacontrol.model.Persona;

public interface PersonaService {
    Persona guardar(Persona persona);
    List<Persona> obtenerTodos();
    Optional<Persona> obtenerPorId(Long id);
    void eliminar(Long id);
}