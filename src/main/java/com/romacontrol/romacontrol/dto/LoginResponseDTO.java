package com.romacontrol.romacontrol.dto;

import java.util.List;

public class LoginResponseDTO {
    private Long id;
    private String dni;
    private String nombre;
    private String apellido;
    private List<String> roles;

    public LoginResponseDTO(Long id, String dni, String nombre, String apellido, List<String> roles) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.roles = roles;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }
}
