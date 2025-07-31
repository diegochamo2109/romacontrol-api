package com.romacontrol.romacontrol.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UsuarioDetalleDTO {
    private Long id;

    private String dni;
    private String pin;

    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;

    private String calle;
    private String numero;
    private String celularCodArea;
    private String celularNumero;
    private String email;

    private Long generoId;
    private String generoNombre;

    private Long cuotaId;
    private String cuotaDescripcion;

    private List<Long> rolesIds;
    private List<String> roles;

    private String contactoNombre;
    private String contactoApellido;
    private String contactoCodArea;
    private String contactoNumero;
    private String contactoVinculo;

    private boolean activo;
    private String creadoPor;
    private LocalDateTime fechaCreacion;
} 
