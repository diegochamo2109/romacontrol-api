package com.romacontrol.romacontrol.model;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"usuario"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String dni;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String calle;
    private String numero;
    private String email;


    private String celularCodArea;
    private String celularNumero;

    @ManyToOne
    @JoinColumn(name = "genero_id")
    private Genero genero;

    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
    private Usuario usuario;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contacto_urgencia_id")
    private ContactoUrgencia contactoUrgencia;

    // 🖼️ Imagen de perfil en la BD
    @Lob
    private byte[] imagenPerfil;
}
