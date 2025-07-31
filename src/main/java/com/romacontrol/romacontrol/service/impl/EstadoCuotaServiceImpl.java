package com.romacontrol.romacontrol.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.romacontrol.romacontrol.model.EstadoCuota;
import com.romacontrol.romacontrol.repository.EstadoCuotaRepository;
import com.romacontrol.romacontrol.service.EstadoCuotaService;

@Service
public class EstadoCuotaServiceImpl implements EstadoCuotaService {

    @Autowired
    private EstadoCuotaRepository estadoCuotaRepository;

    @Override
    public EstadoCuota guardar(EstadoCuota estadoCuota) {
        return estadoCuotaRepository.save(estadoCuota);
    }

    @Override
    public List<EstadoCuota> obtenerTodos() {
        return estadoCuotaRepository.findAll();
    }

    @Override
    public Optional<EstadoCuota> obtenerPorId(Long id) {
        return estadoCuotaRepository.findById(id);
    }

    @Override
    public void eliminar(Long id) {
        estadoCuotaRepository.deleteById(id);
    }
}
