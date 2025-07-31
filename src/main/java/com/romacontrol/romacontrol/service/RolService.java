
package com.romacontrol.romacontrol.service;

import java.util.List;
import java.util.Optional;

import com.romacontrol.romacontrol.model.Rol;

public interface RolService {
    Rol guardar(Rol rol);
    List<Rol> obtenerTodos();
    Optional<Rol> obtenerPorId(Long id);
    void eliminar(Long id);
    Optional<Rol> actualizar(Long id, Rol rolActualizado);
    Optional<Rol> pausar(Long id);
}