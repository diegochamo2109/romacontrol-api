package com.romacontrol.romacontrol.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romacontrol.romacontrol.dto.AuthRequest;
import com.romacontrol.romacontrol.dto.LoginResponseDTO;
import com.romacontrol.romacontrol.model.Usuario;
import com.romacontrol.romacontrol.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired  
    private UsuarioService usuarioService;

    @PostMapping("/login")
public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request) {
    Optional<Usuario> usuarioOptional = usuarioService.buscarPorDni(request.getDni());

    if (usuarioOptional.isEmpty()) {
        return ResponseEntity.status(401).body("DNI inválido");
    }

    Usuario usuario = usuarioOptional.get();

    // ✅ Verificar si el usuario está activo
    if (!usuario.isActivo()) {
        return ResponseEntity.status(403).body("Usuario momentáneamente pausado");
    }

    // Validación del PIN
    if (!request.getPin().equals(usuario.getPin())) {
        return ResponseEntity.status(401).body("PIN incorrecto");
    }

    List<String> roles = usuario.getRoles().stream()
        .map(rol -> rol.getDescripcion().toUpperCase())
        .toList();

    return ResponseEntity.ok(new LoginResponseDTO(
        usuario.getId(),
        usuario.getDni(),
        usuario.getPersona().getNombre(),
        usuario.getPersona().getApellido(),
        roles
    ));
}}

