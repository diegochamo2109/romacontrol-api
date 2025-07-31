package com.romacontrol.romacontrol.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.romacontrol.romacontrol.dto.CuotaMensualDTO;
import com.romacontrol.romacontrol.model.CuotaMensual;
import com.romacontrol.romacontrol.service.CuotaMensualService;

@RestController
@RequestMapping("/api/cuotas")
@CrossOrigin(origins = "*")
public class CuotaMensualController {

    @Autowired
    private CuotaMensualService cuotaMensualService;

    // 🎯 Crear cuota y (opcional) asignarla automáticamente
    @PostMapping("/crear")
    public ResponseEntity<CuotaMensual> crearCuota(
            @RequestBody CuotaMensual cuota,
            @RequestParam(defaultValue = "false") boolean asignar) {

        CuotaMensual nuevaCuota = cuotaMensualService.crearCuota(cuota, asignar);
        return ResponseEntity.ok(nuevaCuota);
    }

    // 🔎 Obtener todas las cuotas creadas
    @GetMapping
    public ResponseEntity<List<CuotaMensual>> listarTodas() {
        return ResponseEntity.ok(cuotaMensualService.obtenerTodas());
    }

    // 🔍 Buscar la próxima cuota activa (para alta de usuario)
    @GetMapping("/proxima")
    public ResponseEntity<?> obtenerProximaCuota() {
        return cuotaMensualService.buscarProximaCuotaActiva(LocalDate.now())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Listar solo las cuotas activas (para el formulario de alta de usuario)
    @GetMapping("/activas")
    public List<CuotaMensualDTO> listarCuotasActivas() {
        return cuotaMensualService.listarActivas(); // método ya implementado
    }
}
