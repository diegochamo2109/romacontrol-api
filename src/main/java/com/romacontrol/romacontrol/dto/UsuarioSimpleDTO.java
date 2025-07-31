package com.romacontrol.romacontrol.dto;

public record UsuarioSimpleDTO(
    Long id,
    String dni,
    String nombre,
    String apellido
) {}
