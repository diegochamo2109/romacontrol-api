package com.romacontrol.romacontrol.service;

import java.util.List;

import com.romacontrol.romacontrol.dto.CuotaSimpleDTO;
import com.romacontrol.romacontrol.dto.PagoHistorialDTO;
import com.romacontrol.romacontrol.dto.PagoRequestDTO;
import com.romacontrol.romacontrol.model.CuotaMensual;
import com.romacontrol.romacontrol.model.Pago;

public interface PagoService {

    /**
     * Registra un nuevo pago basado en el DTO recibido.
     */
    Pago registrarPago(PagoRequestDTO dto);

    /**
     * Devuelve la lista completa de CuotaMensual impagas (versión sin filtrar).
     */
    List<CuotaMensual> obtenerCuotasImpagas(Long usuarioId);

    /**
     * Devuelve una lista limpia y simplificada de cuotas impagas (usando DTO).
     */
    List<CuotaSimpleDTO> obtenerCuotasImpagasDTO(Long usuarioId);

    List<PagoHistorialDTO> obtenerHistorialPagosPorUsuario(Long usuarioId);

}
