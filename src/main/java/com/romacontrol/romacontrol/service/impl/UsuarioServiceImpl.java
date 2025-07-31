package com.romacontrol.romacontrol.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.romacontrol.romacontrol.dto.UsuarioDetalleDTO;
import com.romacontrol.romacontrol.dto.UsuarioEditDTO;
import com.romacontrol.romacontrol.dto.UsuarioRequestDTO;
import com.romacontrol.romacontrol.dto.UsuarioResponseDTO;
import com.romacontrol.romacontrol.model.ContactoUrgencia;
import com.romacontrol.romacontrol.model.CuotaMensual;
import com.romacontrol.romacontrol.model.Genero;
import com.romacontrol.romacontrol.model.Pago;
import com.romacontrol.romacontrol.model.Persona;
import com.romacontrol.romacontrol.model.Rol;
import com.romacontrol.romacontrol.model.Usuario;
import com.romacontrol.romacontrol.repository.ContactoUrgenciaRepository;
import com.romacontrol.romacontrol.repository.CuotaMensualRepository;
import com.romacontrol.romacontrol.repository.GeneroRepository;
import com.romacontrol.romacontrol.repository.PagoRepository;
import com.romacontrol.romacontrol.repository.PersonaRepository;
import com.romacontrol.romacontrol.repository.RolRepository;
import com.romacontrol.romacontrol.repository.UsuarioRepository;
import com.romacontrol.romacontrol.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private ContactoUrgenciaRepository contactoUrgenciaRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private CuotaMensualRepository cuotaMensualRepository;

    @Autowired
    private PagoRepository pagoRepository;

    @Override
public void pausar(Long id) throws Exception {
    Usuario usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new Exception("Usuario no encontrado con ID: " + id));

    if (!usuario.isActivo()) {
        throw new Exception("El usuario ya está inactivo");
    }

    usuario.setActivo(false);
    usuario.setFechaModificacion(LocalDate.now());
    usuarioRepository.save(usuario);
}


   @Override
    @Transactional
    public Usuario actualizar(Long id, UsuarioEditDTO dto, MultipartFile imagen) throws Exception {
    Usuario usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new Exception("Usuario no encontrado con ID: " + id));

    Persona persona = usuario.getPersona();

    // ✅ Actualizar datos personales
    persona.setNombre(dto.getNombre());
    persona.setApellido(dto.getApellido());
    persona.setFechaNacimiento(dto.getFechaNacimiento());
    persona.setGenero(generoRepository.findById(dto.getGeneroId())
        .orElseThrow(() -> new RuntimeException("Género no encontrado con ID: " + dto.getGeneroId())));
    persona.setCalle(dto.getCalle());
    persona.setNumero(dto.getNumero());
    persona.setCelularCodArea(dto.getCelularCodArea());
    persona.setCelularNumero(dto.getCelularNumero());
    persona.setEmail(dto.getEmail());

    // ✅ Actualizar contacto de urgencia
    ContactoUrgencia contacto = persona.getContactoUrgencia();
    if (contacto != null) {
        contacto.setNombre(dto.getContactoNombre());
        contacto.setApellido(dto.getContactoApellido());
        contacto.setCelularCodArea(dto.getContactoCodArea());
        contacto.setCelularNumero(dto.getContactoNumero());
        contacto.setVinculo(dto.getContactoVinculo());
        contactoUrgenciaRepository.save(contacto);
    }

    // ✅ Actualizar imagen si se envió una nueva
    if (imagen != null && !imagen.isEmpty()) {
        usuario.setImagenPerfil(imagen.getBytes());
    }

    // ✅ Actualizar PIN si el usuario quiere cambiarlo
    if (dto.getPin() != null && !dto.getPin().isBlank()) {
        usuario.setPin(dto.getPin()); // ⚠️ Texto plano, como solicitaste
    }

    // ❌ No se actualizan los campos: DNI, cuota, roles, activo

    // ✅ Fecha modificación
    usuario.setFechaModificacion(LocalDateTime.now().toLocalDate());

    return usuarioRepository.save(usuario);
}

    //Método obtenerDetalle(Long id)//

@Override
public UsuarioDetalleDTO obtenerDetalle(Long id) throws Exception {
    Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new Exception("Usuario no encontrado con ID: " + id));

    Persona persona = usuario.getPersona();
    ContactoUrgencia contacto = persona.getContactoUrgencia();
    Genero genero = persona.getGenero();

    List<Long> rolesIds = usuario.getRoles().stream()
        .map(Rol::getId)
        .collect(Collectors.toList());

    List<String> roles = usuario.getRoles().stream()
        .map(Rol::getDescripcion)
        .collect(Collectors.toList());

    Optional<Pago> pagoOpt = pagoRepository.findByUsuario(usuario).stream().findFirst();
    Long cuotaId = null;
    String cuotaDescripcion = null;
    if (pagoOpt.isPresent()) {
        cuotaId = pagoOpt.get().getCuotaMensual().getId();
        cuotaDescripcion = pagoOpt.get().getCuotaMensual().getDescripcion();
    }

return UsuarioDetalleDTO.builder()
    .id(usuario.getId())
    .dni(usuario.getDni())
    .pin(usuario.getPin())
    .nombre(persona.getNombre())
    .apellido(persona.getApellido())
    .fechaNacimiento(persona.getFechaNacimiento())
    .calle(persona.getCalle())
    .numero(persona.getNumero())
    .celularCodArea(persona.getCelularCodArea())
    .celularNumero(persona.getCelularNumero())
    .email(persona.getEmail())
    .generoId(genero.getId())
    .generoNombre(genero.getNombre())
    .cuotaId(cuotaId)
    .cuotaDescripcion(cuotaDescripcion)
    .rolesIds(rolesIds)
    .roles(roles)
    .contactoNombre(contacto.getNombre())
    .contactoApellido(contacto.getApellido())
    .contactoCodArea(contacto.getCelularCodArea())
    .contactoNumero(contacto.getCelularNumero())
    .contactoVinculo(contacto.getVinculo())
    .activo(usuario.isActivo())
    .creadoPor(usuario.getCreadoPor() != null
        ? usuario.getCreadoPor().getPersona().getNombre() + " " + usuario.getCreadoPor().getPersona().getApellido()
        : "—")
    .fechaCreacion(usuario.getFechaCreacion().atStartOfDay())
    .build();

}

  
  

    @Override
    @Transactional
    public Usuario guardar(UsuarioRequestDTO dto, MultipartFile imagen) {

        if (dto.getFechaNacimiento() != null) {
            int edad = Period.between(dto.getFechaNacimiento(), LocalDate.now()).getYears();
            if (edad < 15) {
                throw new RuntimeException("El usuario debe tener al menos 15 años.");
            }
        }

        if (usuarioRepository.existsByDni(dto.getDni())) {
            throw new RuntimeException("Ya existe un usuario con el mismo DNI.");
        }

        String nombre = capitalizar(dto.getNombre());
        String apellido = capitalizar(dto.getApellido());
        String calle = capitalizar(dto.getCalle());

        ContactoUrgencia contacto = ContactoUrgencia.builder()
                .nombre(capitalizar(dto.getContactoNombre()))
                .apellido(capitalizar(dto.getContactoApellido()))
                .celularCodArea(dto.getContactoCodArea())
                .celularNumero(dto.getContactoNumero())
                .vinculo(capitalizar(dto.getContactoVinculo()))
                .build();
        contactoUrgenciaRepository.save(contacto);

        Genero genero = generoRepository.findById(dto.getGeneroId())
                .orElseThrow(() -> new RuntimeException("Género no encontrado"));

        byte[] imagenBytes = null;
        if (imagen != null && !imagen.isEmpty()) {
            try {
                imagenBytes = imagen.getBytes();
            } catch (IOException e) {
                throw new RuntimeException("Error al leer la imagen: " + e.getMessage());
            }
        }

        Persona persona = Persona.builder()
                .nombre(nombre)
                .apellido(apellido)
                .fechaNacimiento(dto.getFechaNacimiento())
                .calle(calle)
                .numero(dto.getNumero())
                .celularCodArea(dto.getCelularCodArea())
                .celularNumero(dto.getCelularNumero())
                .email(dto.getEmail())
                .contactoUrgencia(contacto)
                .genero(genero)
                .imagenPerfil(imagenBytes)
                .build();

        Set<Rol> roles = new HashSet<>();
        for (Long rolId : dto.getRolesIds()) {
            Rol rol = rolRepository.findById(rolId)
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + rolId));
            roles.add(rol);
        }

        Usuario creadoPor = usuarioRepository.findById(dto.getCreadoPorUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario creador no encontrado"));

        Usuario usuario = Usuario.builder()
                .dni(dto.getDni())
                .username(dto.getDni())
                .pin(dto.getPin())
                .persona(persona)
                .roles(roles)
                .creadoPor(creadoPor)
                .fechaCreacion(LocalDate.now())
                .fechaModificacion(LocalDate.now())
                .build();

        persona.setUsuario(usuario);
        personaRepository.save(persona);

        // Buscar la próxima cuota activa
        Optional<CuotaMensual> cuotaActivaOpt = cuotaMensualRepository.findProximaCuotaActiva(LocalDate.now());
        if (cuotaActivaOpt.isEmpty()) {
            throw new RuntimeException("No hay cuota activa disponible para asignar al nuevo usuario.");
        }
        
        // ✅ Validar que la cuota no tenga fecha futura
        if (cuotaActivaOpt.get().getFechaLimitePago().isBefore(LocalDate.now())) {
            throw new RuntimeException("⚠️ No se pueden asignar cuotas con fecha futura al registrar un nuevo usuario.");
        }

        // Registrar cuota como pago pendiente
        Pago nuevoPago = Pago.builder()
                .usuario(usuario)
                .cuotaMensual(cuotaActivaOpt.get())
                .estadoPago("Pendiente")
                .build();
        pagoRepository.save(nuevoPago);

        return usuario;
    }

    @Override
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    @Override
public List<UsuarioResponseDTO> buscarPorDniNombreOApellido(String filtro) {
    return usuarioRepository.buscarPorDniNombreOApellido(filtro).stream().map(usuario -> {
        String nombreCompleto = usuario.getPersona().getNombre() + " " + usuario.getPersona().getApellido();

        List<String> roles = usuario.getRoles().stream()
            .map(Rol::getDescripcion)
            .collect(Collectors.toList());

        String creadoPor = usuario.getCreadoPor() != null
            ? usuario.getCreadoPor().getPersona().getNombre() + " " + usuario.getCreadoPor().getPersona().getApellido()
            : "—";

        return UsuarioResponseDTO.builder()
            .id(usuario.getId())
            .dni(usuario.getDni())
            .nombreCompleto(nombreCompleto)
            .roles(roles)
            .activo(usuario.isActivo())
            .fechaCreacion(usuario.getFechaCreacion().atStartOfDay())
            .creadoPor(creadoPor)
            .build();
    }).collect(Collectors.toList());
}


    @Override
        public Optional<Usuario> buscarPorDni(String dni) {
    return usuarioRepository.findByDni(dni);
}


    @Override
    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }
        
    @Override
public Optional<Usuario> buscarOptionalPorDni(String dni) {
    return usuarioRepository.findByDni(dni);
}

    @Override
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }

    

    private String capitalizar(String texto) {
        if (texto == null || texto.isEmpty()) return texto;
        return texto.substring(0, 1).toUpperCase() + texto.substring(1).toLowerCase();} 
    
    // 🔹 NUEVO: Listar todos los usuarios



// 🔹 NUEVO: Listar todos los usuarios

@Override
public List<UsuarioResponseDTO> listarTodos() {
    return usuarioRepository.findAll().stream().map((Usuario usuario) -> {
        String nombreCompleto = usuario.getPersona().getNombre() + " " + usuario.getPersona().getApellido();

        List<String> roles = usuario.getRoles().stream()
            .map(rol -> rol.getDescripcion())
            .collect(Collectors.toList());

        String creadoPor = usuario.getCreadoPor() != null
            ? usuario.getCreadoPor().getPersona().getNombre() + " " + usuario.getCreadoPor().getPersona().getApellido()
            : "—";

        return UsuarioResponseDTO.builder()
            .id(usuario.getId())  // ✅ ESTA LÍNEA ES CLAVE
            .dni(usuario.getDni())
            .nombreCompleto(nombreCompleto)
            .roles(roles)
            .activo(usuario.isActivo())
            .fechaCreacion(usuario.getFechaCreacion().atStartOfDay()) // ✅ CORREGIDO
            .creadoPor(creadoPor)
            .build();
    }).collect(Collectors.toList());
}


}