package com.romacontrol.romacontrol.dto;

import java.time.LocalDate;

public record CuotaSimpleDTO(
    Long id,
    String descripcion,
    Integer anio,
    Double importe,
    LocalDate fechaLimitePago,
    Boolean activa
) {}
