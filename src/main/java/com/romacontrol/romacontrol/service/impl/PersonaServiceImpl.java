
package com.romacontrol.romacontrol.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.romacontrol.romacontrol.model.Persona;
import com.romacontrol.romacontrol.repository.PersonaRepository;
import com.romacontrol.romacontrol.service.PersonaService;

@Service
public class PersonaServiceImpl implements PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Override
    public Persona guardar(Persona persona) {
        return personaRepository.save(persona);
    }

    @Override
    public List<Persona> obtenerTodos() {
        return personaRepository.findAll();
    }

    @Override
    public Optional<Persona> obtenerPorId(Long id) {
        return personaRepository.findById(id);
    }

    @Override
    public void eliminar(Long id) {
        personaRepository.deleteById(id);
    }
}