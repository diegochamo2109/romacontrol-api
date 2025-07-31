package com.romacontrol.romacontrol.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;

    @ElementCollection
    private List<String> permisos;

    private LocalDate fechaCreacion;

    private Boolean activo;

    @JsonIgnore // ⬅️ Esta es la solución que evita el ciclo sin romper el JSON
    @ManyToMany(mappedBy = "roles")
    private Set<Usuario> usuarios;

    @PrePersist
    public void initFechaYEstado() {
        this.fechaCreacion = LocalDate.now();
        this.activo = true;
    }
}
