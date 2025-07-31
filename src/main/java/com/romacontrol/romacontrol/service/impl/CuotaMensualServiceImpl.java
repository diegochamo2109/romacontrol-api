package com.romacontrol.romacontrol.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.romacontrol.romacontrol.dto.CuotaMensualDTO;
import com.romacontrol.romacontrol.model.CuotaMensual;
import com.romacontrol.romacontrol.model.Pago;
import com.romacontrol.romacontrol.model.Usuario;
import com.romacontrol.romacontrol.repository.CuotaMensualRepository;
import com.romacontrol.romacontrol.repository.PagoRepository;
import com.romacontrol.romacontrol.repository.UsuarioRepository;
import com.romacontrol.romacontrol.service.CuotaMensualService;


@Service
public class CuotaMensualServiceImpl implements CuotaMensualService {

    @Autowired
    private CuotaMensualRepository cuotaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PagoRepository pagoRepository;

    @Override
    public CuotaMensual crearCuota(CuotaMensual cuota, boolean asignar) {
        cuota.setActiva(cuota.getActiva() != null && cuota.getActiva()); // evitar null
        CuotaMensual guardada = cuotaRepository.save(cuota);

        if (asignar && cuota.getActiva()) {
            List<Usuario> usuarios = usuarioRepository.findAll();
            for (Usuario usuario : usuarios) {
                Pago pago = Pago.builder()
                        .usuario(usuario)
                        .cuotaMensual(guardada)
                        .estadoPago("Pendiente")
                        .build();
                pagoRepository.save(pago);
            }
        }

        return guardada;
    }

    @Override
    public List<CuotaMensual> obtenerTodas() {
        return cuotaRepository.findAll();
    }
    

    @Override
    public Optional<CuotaMensual> buscarProximaCuotaActiva(LocalDate fecha) {
        return cuotaRepository.findProximaCuotaActiva(fecha);
    }
    // ✅ NUEVO: Para el select de cuotas activas en el frontend
    @Override
    public List<CuotaMensualDTO> listarActivas() {
        return cuotaRepository.findByActivaTrue().stream()
                .map(c -> new CuotaMensualDTO(c.getId(), c.getDescripcion()))
                .collect(Collectors.toList());
    }
}
