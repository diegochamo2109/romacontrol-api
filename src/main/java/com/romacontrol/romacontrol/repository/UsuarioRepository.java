package com.romacontrol.romacontrol.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.romacontrol.romacontrol.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByDni(String dni);

    boolean existsByDni(String dni); // ya lo estás usando
    @Query("""
    SELECT u FROM Usuario u
    WHERE CAST(u.dni AS string) LIKE CONCAT('%', :filtro, '%')
       OR LOWER(u.persona.nombre) LIKE LOWER(CONCAT('%', :filtro, '%'))
       OR LOWER(u.persona.apellido) LIKE LOWER(CONCAT('%', :filtro, '%'))
""")
List<Usuario> buscarPorDniNombreOApellido(@Param("filtro") String filtro);

}
