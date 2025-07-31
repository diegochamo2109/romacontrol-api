package com.romacontrol.romacontrol.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.romacontrol.romacontrol.dto.CuotaSimpleDTO;
import com.romacontrol.romacontrol.dto.PagoHistorialDTO;
import com.romacontrol.romacontrol.dto.PagoRequestDTO;
import com.romacontrol.romacontrol.model.CuotaMensual;
import com.romacontrol.romacontrol.model.Pago;
import com.romacontrol.romacontrol.model.Usuario;
import com.romacontrol.romacontrol.repository.CuotaMensualRepository;
import com.romacontrol.romacontrol.repository.PagoRepository;
import com.romacontrol.romacontrol.repository.UsuarioRepository;
import com.romacontrol.romacontrol.service.PagoService;

@Service
public class PagoServiceImpl implements PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private CuotaMensualRepository cuotaMensualRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

@Override
public Pago registrarPago(PagoRequestDTO dto) {
    // ❗ Validación importante: el sistema no debe aceptar pagos sin usuario logueado
    if (dto.registradoPorUsuarioId() == null) {
        throw new IllegalArgumentException("Debe estar logueado un usuario para registrar el pago.");
    }

    Usuario usuario = usuarioRepository.findById(dto.usuarioId())
        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

    CuotaMensual cuota = cuotaMensualRepository.findById(dto.cuotaMensualId())
        .orElseThrow(() -> new IllegalArgumentException("Cuota no encontrada"));

    Usuario registradoPor = usuarioRepository.findById(dto.registradoPorUsuarioId())
        .orElseThrow(() -> new IllegalArgumentException("Registrador no encontrado"));

    // 🔒 VALIDACIÓN: evitar pagos duplicados
    pagoRepository.findByUsuarioIdAndCuotaMensualIdAndEstadoPago(usuario.getId(), cuota.getId(), "Pagado")
        .ifPresent(p -> { throw new IllegalArgumentException("Este pago ya fue registrado previamente."); });

    // 📅 Verificamos si la fecha límite ya venció
    String estado = LocalDateTime.now().toLocalDate().isAfter(cuota.getFechaLimitePago())
        ? "Pagado con retraso"
        : "Pagado";

    Pago pago = new Pago();
    pago.setUsuario(usuario);
    pago.setCuotaMensual(cuota);
    pago.setFormaPago(dto.formaPago());
    pago.setFechaPago(LocalDateTime.now());
    pago.setRegistradoPor(registradoPor);
    pago.setEstadoPago(estado);
    pago.setMonto(cuota.getImporte());

    return pagoRepository.save(pago);
}


    @Override
    public List<CuotaMensual> obtenerCuotasImpagas(Long usuarioId) {
        return cuotaMensualRepository.findCuotasImpagasPorUsuarioId(usuarioId);
    }
   @Override
public List<PagoHistorialDTO> obtenerHistorialPagosPorUsuario(Long usuarioId) {
    return pagoRepository.findByUsuarioId(usuarioId)
        .stream()
        .map(pago -> new PagoHistorialDTO(
            pago.getUsuario().getDni(), // ✅ nuevo campo
            pago.getCuotaMensual().getDescripcion(),
            pago.getCuotaMensual().getAnio(),
            pago.getMonto(),
            pago.getFormaPago(),
            pago.getEstadoPago(),
            pago.getFechaPago(),
            pago.getRegistradoPor().getPersona().getNombre() + " " + pago.getRegistradoPor().getPersona().getApellido()
        ))
        .toList();
}


    // ✅ NUEVO MÉTODO USANDO DTO
    @Override
    public List<CuotaSimpleDTO> obtenerCuotasImpagasDTO(Long usuarioId) {
        return cuotaMensualRepository.findCuotasImpagasPorUsuarioId(usuarioId)
            .stream()
            .map(c -> new CuotaSimpleDTO(
                c.getId(),
                c.getDescripcion(),
                c.getAnio(),
                c.getImporte(),
                c.getFechaLimitePago(),
                c.getActiva()
            ))
            .toList();
    }
}
