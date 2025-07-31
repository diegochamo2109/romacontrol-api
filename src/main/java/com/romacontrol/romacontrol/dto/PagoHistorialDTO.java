package com.romacontrol.romacontrol.dto;

import java.time.LocalDateTime;

public record PagoHistorialDTO(
    String dni, 
    String descripcionCuota,
    int anio,
    Double importe,
    String formaPago,
    String estadoPago,
    LocalDateTime fechaPago,
    String registradoPor
) {}
