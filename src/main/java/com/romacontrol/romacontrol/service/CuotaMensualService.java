package com.romacontrol.romacontrol.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.romacontrol.romacontrol.dto.CuotaMensualDTO;
import com.romacontrol.romacontrol.model.CuotaMensual;

public interface CuotaMensualService {

    CuotaMensual crearCuota(CuotaMensual cuota, boolean asignar);

    List<CuotaMensual> obtenerTodas();

    Optional<CuotaMensual> buscarProximaCuotaActiva(LocalDate fecha);

    List<CuotaMensualDTO> listarActivas(); // ← este es el que falta declarar
}
