
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

import com.romacontrol.romacontrol.model.Genero;
import com.romacontrol.romacontrol.service.GeneroService;

@RestController
@RequestMapping("/api/generos")
@CrossOrigin(origins = "*")
public class GeneroController {

    @Autowired
    private GeneroService generoService;

    @PostMapping
    public Genero crear(@RequestBody Genero genero) {
        return generoService.guardar(genero);
    }

    @GetMapping
    public List<Genero> listar() {
        return generoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Optional<Genero> obtenerPorId(@PathVariable Long id) {
        return generoService.obtenerPorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        generoService.eliminar(id);
    }
}
