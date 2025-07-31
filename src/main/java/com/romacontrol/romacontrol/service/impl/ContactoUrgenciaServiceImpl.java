package com.romacontrol.romacontrol.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.romacontrol.romacontrol.model.ContactoUrgencia;
import com.romacontrol.romacontrol.repository.ContactoUrgenciaRepository;
import com.romacontrol.romacontrol.service.ContactoUrgenciaService;

@Service
public class ContactoUrgenciaServiceImpl implements ContactoUrgenciaService {

    @Autowired
    private ContactoUrgenciaRepository contactoUrgenciaRepository;

    @Override
    public ContactoUrgencia guardar(ContactoUrgencia contacto) {
        return contactoUrgenciaRepository.save(contacto);
    }

    @Override
    public List<ContactoUrgencia> obtenerTodos() {
        return contactoUrgenciaRepository.findAll();
    }

    @Override
    public Optional<ContactoUrgencia> obtenerPorId(Long id) {
        return contactoUrgenciaRepository.findById(id);
    }

    @Override
    public void eliminar(Long id) {
        contactoUrgenciaRepository.deleteById(id);
    }
}
