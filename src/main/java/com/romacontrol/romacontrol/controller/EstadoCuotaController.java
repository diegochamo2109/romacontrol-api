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

import com.romacontrol.romacontrol.model.EstadoCuota;
import com.romacontrol.romacontrol.service.EstadoCuotaService;

@RestController
@RequestMapping("/api/estados")
@CrossOrigin(origins = "*")
public class EstadoCuotaController {

    @Autowired
    private EstadoCuotaService estadoService;

    @PostMapping
    public EstadoCuota crear(@RequestBody EstadoCuota estado) {
        return estadoService.guardar(estado);
    }

    @GetMapping
    public List<EstadoCuota> listar() {
        return estadoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Optional<EstadoCuota> obtenerPorId(@PathVariable Long id) {
        return estadoService.obtenerPorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        estadoService.eliminar(id);
    }
}
