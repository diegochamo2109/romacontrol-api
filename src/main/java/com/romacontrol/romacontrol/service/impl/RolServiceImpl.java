package com.romacontrol.romacontrol.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.romacontrol.romacontrol.model.Rol;
import com.romacontrol.romacontrol.repository.RolRepository;
import com.romacontrol.romacontrol.service.RolService;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;

    @Override
    public Rol guardar(Rol rol) {
        return rolRepository.save(rol);
    }

    @Override
    public List<Rol> obtenerTodos() {
        return rolRepository.findAll();
    }

    @Override
    public Optional<Rol> obtenerPorId(Long id) {
        return rolRepository.findById(id);
    }

    @Override
    public void eliminar(Long id) {
        rolRepository.deleteById(id);
    }

    @Override
    public Optional<Rol> actualizar(Long id, Rol rolActualizado) {
        return rolRepository.findById(id).map(rol -> {
            rol.setDescripcion(rolActualizado.getDescripcion());
            rol.setPermisos(rolActualizado.getPermisos());
            rol.setActivo(rolActualizado.getActivo());
            return rolRepository.save(rol);
        });
    }

    @Override
    public Optional<Rol> pausar(Long id) {
        return rolRepository.findById(id).map(rol -> {
            rol.setActivo(!rol.getActivo());
            return rolRepository.save(rol);
        });
    }
}