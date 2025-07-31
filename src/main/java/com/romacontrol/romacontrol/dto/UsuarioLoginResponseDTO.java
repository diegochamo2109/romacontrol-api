package com.romacontrol.romacontrol.dto;

import lombok.Data;

@Data
public class UsuarioLoginResponseDTO {
    private Long id;
    private String dni;
    private String nombre;
    private String apellido;
}
