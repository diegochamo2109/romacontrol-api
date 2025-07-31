package com.romacontrol.romacontrol.service;

import java.util.List;
import java.util.Optional;

import com.romacontrol.romacontrol.model.EstadoCuota;

public interface EstadoCuotaService {
    EstadoCuota guardar(EstadoCuota estado);
    List<EstadoCuota> obtenerTodos();
    Optional<EstadoCuota> obtenerPorId(Long id);
    void eliminar(Long id);
}
