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

import com.romacontrol.romacontrol.model.ContactoUrgencia;
import com.romacontrol.romacontrol.service.ContactoUrgenciaService;

@RestController
@RequestMapping("/api/contactos")
@CrossOrigin(origins = "*")
public class ContactoUrgenciaController {

    @Autowired
    private ContactoUrgenciaService contactoService;

    @PostMapping
    public ContactoUrgencia crear(@RequestBody ContactoUrgencia contacto) {
        return contactoService.guardar(contacto);
    }

    @GetMapping
    public List<ContactoUrgencia> listar() {
        return contactoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Optional<ContactoUrgencia> obtenerPorId(@PathVariable Long id) {
        return contactoService.obtenerPorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        contactoService.eliminar(id);
    }
}
