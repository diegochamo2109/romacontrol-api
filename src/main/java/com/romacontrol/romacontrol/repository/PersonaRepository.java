package com.romacontrol.romacontrol.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.romacontrol.romacontrol.model.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
    Optional<Persona> findByDni(String dni);
}