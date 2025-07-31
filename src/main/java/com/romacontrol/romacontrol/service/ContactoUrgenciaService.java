package com.romacontrol.romacontrol.service;

import java.util.List;
import java.util.Optional;

import com.romacontrol.romacontrol.model.ContactoUrgencia;

public interface ContactoUrgenciaService {
    ContactoUrgencia guardar(ContactoUrgencia contacto);
    List<ContactoUrgencia> obtenerTodos();
    Optional<ContactoUrgencia> obtenerPorId(Long id);
    void eliminar(Long id);
}