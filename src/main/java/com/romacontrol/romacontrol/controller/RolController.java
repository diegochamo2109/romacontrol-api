package com.romacontrol.romacontrol.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romacontrol.romacontrol.model.Rol;
import com.romacontrol.romacontrol.service.RolService;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
public class RolController {

    @Autowired
    private RolService rolService;

    @PostMapping
    public Rol crear(@RequestBody Rol rol) {
        return rolService.guardar(rol);
    }

    @GetMapping
    public List<Rol> listar() {
        return rolService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Optional<Rol> obtenerPorId(@PathVariable Long id) {
        return rolService.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public Optional<Rol> editar(@PathVariable Long id, @RequestBody Rol rolActualizado) {
        return rolService.actualizar(id, rolActualizado);
    }

    @PatchMapping("/{id}/pausar")
    public Optional<Rol> pausar(@PathVariable Long id) {
        return rolService.pausar(id);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        rolService.eliminar(id);
    }
}