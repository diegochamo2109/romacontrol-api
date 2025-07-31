package com.romacontrol.romacontrol.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romacontrol.romacontrol.model.Persona;
import com.romacontrol.romacontrol.service.PersonaService;

@RestController
@RequestMapping("/api/personas")
@CrossOrigin(origins = "*")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @PostMapping
    public Persona crear(@RequestBody Persona persona) {
        return personaService.guardar(persona);
    }

    @GetMapping
    public List<Persona> listar() {
        return personaService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Optional<Persona> obtenerPorId(@PathVariable Long id) {
        return personaService.obtenerPorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        personaService.eliminar(id);
    }
}
