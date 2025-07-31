package com.romacontrol.romacontrol.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UsuarioEditDTO {

    // 🔒 Campos solo visibles, no modificables
    private String dni;
    private Long cuotaId;
    private List<Long> rolesIds;

    // ✏️ Campos editables (obligatorios)
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotNull(message = "Debe ingresar una fecha de nacimiento")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "La calle es obligatoria")
    private String calle;

    @NotBlank(message = "El número de calle es obligatorio")
    private String numero;

    @NotBlank(message = "El código de área es obligatorio")
    private String celularCodArea;

    @NotBlank(message = "El número de celular es obligatorio")
    private String celularNumero;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ingresar un email válido")
    private String email;

    @NotNull(message = "Debe seleccionar un género")
    private Long generoId;

    // ✏️ Contacto de urgencia
    @NotBlank(message = "El nombre del contacto de urgencia es obligatorio")
    private String contactoNombre;

    @NotBlank(message = "El apellido del contacto de urgencia es obligatorio")
    private String contactoApellido;

    @NotBlank(message = "El código de área del contacto es obligatorio")
    private String contactoCodArea;

    @NotBlank(message = "El número del contacto es obligatorio")
    private String contactoNumero;

    @NotBlank(message = "El vínculo del contacto es obligatorio")
    private String contactoVinculo;

    // 🔑 Campo opcional: solo si desea cambiar el PIN
    @Pattern(regexp = "^\\d{1,4}$", message = "El PIN debe tener hasta 4 dígitos numéricos")
    private String pin;

    // Estado de activación
    private boolean activo;
}
