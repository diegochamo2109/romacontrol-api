package com.romacontrol.romacontrol.model;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/*Entidad que va a ser una tabla median Jpa */
@Entity
/*Lombok genera automáticamente los métodos getX() y setX() para todos los atributos */
@Getter
@Setter
/*Genera el constructor sin argumentos (NoArgsConstructor) y otro con todos los campos (AllArgsConstructor). */
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
/*Permite crear objetos Usuario usando el patrón Builder. 
El toBuilder = true permite hacer .toBuilder() sobre un objeto existente para copiarlo y modificarlo */
@ToString(exclude = {"persona", "roles", "creadoPor", "imagenPerfil"})
/*Genera un toString() automático excluyendo esos campos para evitar bucles infinitos o datos muy pesados. */
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
/*Genera equals() y hashCode() usando solo los campos que tengan @EqualsAndHashCode.Include */
public class Usuario {

    @Id // Indica que este campo es la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // El valor se genera automáticamente en la base de datos (autoincremental)
    @EqualsAndHashCode.Include // Se incluye este campo en equals() y hashCode()
    private Long id;

    private String username; // Nombre de usuario (opcional si se usa DNI como login)
    private String dni;      // DNI del usuario, debe ser único
    private String pin;      // Contraseña (PIN), encriptada si se aplica seguridad correctamente

    @ManyToOne(cascade = CascadeType.ALL) // Relación muchos a uno con Persona, con cascada total
    @JoinColumn(name = "persona_id") // La FK en la tabla será 'persona_id'
    private Persona persona; // Datos personales asociados al usuario

    @Builder.Default // Valor por defecto cuando se usa .builder()
    private boolean activo = true; // Indica si el usuario está habilitado para usar el sistema

    @ManyToMany(fetch = FetchType.EAGER) // Muchos a muchos con Rol, se cargan inmediatamente al obtener el Usuario
    @JoinTable( // Define la tabla intermedia entre Usuario y Rol
        name = "usuario_roles", // Nombre de la tabla intermedia
        joinColumns = @JoinColumn(name = "usuario_id"), // Columna que apunta al Usuario
        inverseJoinColumns = @JoinColumn(name = "rol_id") // Columna que apunta al Rol
    )
    private Set<Rol> roles; // Conjunto de roles asignados al usuario (puede tener más de uno)

    @ManyToOne // Relación muchos a uno (el usuario puede haber sido creado por otro usuario)
    @JoinColumn(name = "creado_por_id") // FK en la tabla 'usuario' que apunta al usuario creador
    private Usuario creadoPor; // Usuario que registró a este usuario

    private LocalDate fechaCreacion;     // Fecha en que se creó el usuario (auditoría)
    private LocalDate fechaModificacion; // Fecha de la última modificación (auditoría)

    @Lob // Campo binario grande (para imágenes)
    @Basic(fetch = FetchType.LAZY) // No se carga automáticamente al traer el usuario (solo cuando se accede a imagenPerfil)
    private byte[] imagenPerfil; // Imagen de perfil del usuario en formato binario
}
