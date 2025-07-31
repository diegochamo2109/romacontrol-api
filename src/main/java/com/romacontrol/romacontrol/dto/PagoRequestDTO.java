package com.romacontrol.romacontrol.dto;

public record PagoRequestDTO(
    Long usuarioId,
    Long cuotaMensualId,
    Double monto,
    Long registradoPorUsuarioId,
    String formaPago
) {}
