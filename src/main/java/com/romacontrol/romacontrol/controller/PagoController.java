package com.romacontrol.romacontrol.controller;

import java.util.List;
import java.util.Map; // ✅ nuevo import necesario

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.romacontrol.romacontrol.dto.CuotaSimpleDTO;
import com.romacontrol.romacontrol.dto.PagoHistorialDTO;
import com.romacontrol.romacontrol.dto.PagoRequestDTO;
import com.romacontrol.romacontrol.model.CuotaMensual;
import com.romacontrol.romacontrol.model.Pago;
import com.romacontrol.romacontrol.service.PagoService;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "*")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    // ✅ Registrar un nuevo pago
        @PostMapping
        public ResponseEntity<?> registrarPago(@RequestBody PagoRequestDTO dto) {
            try {
                Pago pago = pagoService.registrarPago(dto);
                return ResponseEntity.ok(Map.of(
                    "mensaje", "✅ Pago registrado correctamente",
                    "pagoId", pago.getId()
                ));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(Map.of(
                    "error", "Datos inválidos",
                    "mensaje", e.getMessage()
                ));
            } catch (Exception e) {
                return ResponseEntity.internalServerError().body(Map.of(
                    "error", "Error interno",
                    "mensaje", e.getMessage()
                ));
            }
        }

    @GetMapping("/pendientes/raw")
    public ResponseEntity<?> obtenerCuotasImpagasRaw(@RequestParam Long usuarioId) {
        try {
            List<CuotaMensual> cuotas = pagoService.obtenerCuotasImpagas(usuarioId);
            return ResponseEntity.ok(cuotas);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("❌ Error al buscar cuotas impagas: " + e.getMessage());
        }
    }

    @GetMapping("/historial")
public ResponseEntity<?> obtenerHistorialPagos(@RequestParam Long usuarioId) {
    try {
        List<PagoHistorialDTO> historial = pagoService.obtenerHistorialPagosPorUsuario(usuarioId);
        return ResponseEntity.ok(historial);
    } catch (Exception e) {
        // ✅ Devuelve un JSON válido con mensaje de error
        return ResponseEntity.internalServerError().body(
            Map.of("error", "Error al obtener el historial", "detalle", e.getMessage())
        );
    }
}


    @GetMapping("/pendientes")
    public ResponseEntity<?> obtenerCuotasImpagasDTO(@RequestParam Long usuarioId) {
        try {
            List<CuotaSimpleDTO> cuotas = pagoService.obtenerCuotasImpagasDTO(usuarioId);
            return ResponseEntity.ok(cuotas);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("❌ Error al buscar cuotas impagas (DTO): " + e.getMessage());
        }
    }
}
