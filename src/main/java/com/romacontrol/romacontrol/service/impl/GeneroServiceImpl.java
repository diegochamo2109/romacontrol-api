package com.romacontrol.romacontrol.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.romacontrol.romacontrol.model.Genero;
import com.romacontrol.romacontrol.repository.GeneroRepository;
import com.romacontrol.romacontrol.service.GeneroService;

@Service
public class GeneroServiceImpl implements GeneroService {

    @Autowired
    private GeneroRepository generoRepository;

    @Override
    public Genero guardar(Genero genero) {
        return generoRepository.save(genero);
    }

    @Override
    public List<Genero> obtenerTodos() {
        return generoRepository.findAll();
    }

    @Override
    public Optional<Genero> obtenerPorId(Long id) {
        return generoRepository.findById(id);
    }

    @Override
    public void eliminar(Long id) {
        generoRepository.deleteById(id);
    }
}
