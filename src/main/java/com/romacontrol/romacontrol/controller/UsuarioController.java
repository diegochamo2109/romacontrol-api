package com.romacontrol.romacontrol.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.romacontrol.romacontrol.dto.UsuarioDetalleDTO;
import com.romacontrol.romacontrol.dto.UsuarioEditDTO;
import com.romacontrol.romacontrol.dto.UsuarioRequestDTO;
import com.romacontrol.romacontrol.dto.UsuarioResponseDTO;
import com.romacontrol.romacontrol.dto.UsuarioSimpleDTO;
import com.romacontrol.romacontrol.model.Usuario;
import com.romacontrol.romacontrol.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {
    
    @GetMapping("/buscar-filtro")
public ResponseEntity<List<UsuarioResponseDTO>> buscarPorDniNombreOApellido(@RequestParam String filtro) {
    List<UsuarioResponseDTO> resultados = usuarioService.buscarPorDniNombreOApellido(filtro);
    return ResponseEntity.ok(resultados);
}

    // 🔹 NUEVO: Endpoint GET /api/usuarios/{id}

    @GetMapping("/{id}")
public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
    try {
        UsuarioDetalleDTO dto = usuarioService.obtenerDetalle(id);
        return ResponseEntity.ok(dto);
    } catch (Exception e) {
        return ResponseEntity.status(404).body(Map.of("mensaje", "Usuario no encontrado con ID: " + id));
    }
}
    @PutMapping("/{id}/pausar")
public ResponseEntity<String> pausarUsuario(@PathVariable Long id) {
    try {
        usuarioService.pausar(id);
        return ResponseEntity.ok("Usuario desactivado correctamente");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
    }
}


    // 🔹 NUEVO: Endpoint para listar todos los usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> crear(
        @RequestPart("datos") @Valid UsuarioRequestDTO dto,
        @RequestPart(value = "imagenPerfil", required = false) MultipartFile imagen,
        BindingResult result
    ) {
        if (result.hasErrors()) {
            StringBuilder errores = new StringBuilder("Errores en los datos enviados: ");
            result.getFieldErrors().forEach(error ->
                errores.append(String.format("[%s: %s] ", error.getField(), error.getDefaultMessage()))
            );
            return ResponseEntity.badRequest().body(Map.of("mensaje", errores.toString()));
        }

        try {
            Usuario nuevoUsuario = usuarioService.guardar(dto, imagen);
            return ResponseEntity.ok(Map.of("mensaje", "✅ Usuario creado con ID " + nuevoUsuario.getId()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("mensaje", "❌ Error: " + e.getMessage()));
        }
    }

   // @GetMapping
// public List<Usuario> listar() {
//     return usuarioService.obtenerTodos();
@PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<?> actualizarUsuario(
    @PathVariable Long id,
    @RequestPart("datos") @Valid UsuarioEditDTO dto,
    @RequestPart(value = "imagen", required = false) MultipartFile imagen,
    BindingResult result
) {
    if (result.hasErrors()) {
        StringBuilder errores = new StringBuilder("Errores en los datos enviados: ");
        result.getFieldErrors().forEach(error ->
            errores.append(String.format("[%s: %s] ", error.getField(), error.getDefaultMessage()))
        );
        return ResponseEntity.badRequest().body(Map.of("mensaje", errores.toString()));
    }

    try {
        usuarioService.actualizar(id, dto, imagen); // ✅ usa el método correcto
        return ResponseEntity.ok(Map.of("mensaje", "✅ Usuario actualizado correctamente"));
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(Map.of("mensaje", "❌ Error: " + e.getMessage()));
    }
}


    //@GetMapping("/{id}")
    //public Optional<Usuario> obtenerPorId(@PathVariable Long id) {
     //   return usuarioService.obtenerPorId(id);
    //}//

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
    }

@GetMapping("/buscar")
public ResponseEntity<?> buscarPorDni(@RequestParam String dni) {
    Optional<Usuario> usuarioOpt = usuarioService.buscarOptionalPorDni(dni);

    if (usuarioOpt.isPresent()) {
        Usuario usuario = usuarioOpt.get();
        UsuarioSimpleDTO dto = new UsuarioSimpleDTO(
            usuario.getId(),
            usuario.getDni(),
            usuario.getPersona().getNombre(),
            usuario.getPersona().getApellido()
        );
        return ResponseEntity.ok(dto);
    } else {
        return ResponseEntity.status(404).body(Map.of("mensaje", "Usuario no encontrado"));}
    
    
    
}}