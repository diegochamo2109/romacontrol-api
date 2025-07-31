package com.romacontrol.romacontrol.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UsuarioRequestDTO {

    // === Datos de Usuario ===
    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "^[0-9]{7,10}$", message = "El DNI debe contener solo números, entre 7 y 10 dígitos")
    private String dni;

    @NotBlank(message = "El PIN es obligatorio")
    @Pattern(regexp = "^\\d{1,4}$", message = "El PIN debe tener hasta 4 dígitos numéricos")
    private String pin;

    @NotEmpty(message = "Debe seleccionar al menos un rol")
    private List<Long> rolesIds;

    @NotNull(message = "Debe seleccionar una cuota")
    private Long cuotaId;

    // === Datos de Persona ===
    @NotBlank(message = "El nombre es obligatorio")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "El nombre solo debe contener letras")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "El apellido solo debe contener letras")
    private String apellido;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "La calle es obligatoria")
    private String calle;

    @NotBlank(message = "El número de calle es obligatorio")
    @Pattern(regexp = "^[0-9]+$", message = "El número de calle debe contener solo números")
    private String numero;

    @NotBlank(message = "El código de área es obligatorio")
    @Pattern(regexp = "^[0-9]+$", message = "El código de área debe contener solo números")
    private String celularCodArea;

    @NotBlank(message = "El número de celular es obligatorio")
    @Pattern(regexp = "^[0-9]+$", message = "El número de celular debe contener solo números")
    private String celularNumero;

    @Email(message = "El email debe tener un formato válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @NotNull(message = "Debe seleccionar un género")
    private Long generoId;

    private boolean activo = true;

    // === Contacto de Urgencia ===
    @NotBlank(message = "El nombre del contacto es obligatorio")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "El nombre del contacto debe contener solo letras")
    private String contactoNombre;

    @NotBlank(message = "El apellido del contacto es obligatorio")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "El apellido del contacto debe contener solo letras")
    private String contactoApellido;

    @NotBlank(message = "El código de área del contacto es obligatorio")
    @Pattern(regexp = "^[0-9]+$", message = "El código de área del contacto debe contener solo números")
    private String contactoCodArea;

    @NotBlank(message = "El número del contacto es obligatorio")
    @Pattern(regexp = "^[0-9]+$", message = "El número del contacto debe contener solo números")
    private String contactoNumero;

    @NotBlank(message = "El vínculo del contacto es obligatorio")
    private String contactoVinculo;

    // === Auditoría ===
    @NotNull(message = "El ID del usuario creador es obligatorio")
    private Long creadoPorUsuarioId;
}
