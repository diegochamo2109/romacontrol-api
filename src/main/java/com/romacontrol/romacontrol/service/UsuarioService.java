    package com.romacontrol.romacontrol.service;

    import java.util.List;
    import java.util.Optional;

    import org.springframework.web.multipart.MultipartFile;

    import com.romacontrol.romacontrol.dto.UsuarioDetalleDTO;
    import com.romacontrol.romacontrol.dto.UsuarioEditDTO;
    import com.romacontrol.romacontrol.dto.UsuarioRequestDTO;
    import com.romacontrol.romacontrol.dto.UsuarioResponseDTO;
    import com.romacontrol.romacontrol.model.Usuario;

    public interface UsuarioService {
        Usuario guardar(UsuarioRequestDTO dto, MultipartFile imagen);  // alta
        List<Usuario> obtenerTodos();
        Optional<Usuario> obtenerPorId(Long id);
        void eliminar(Long id);
        Optional<Usuario> buscarOptionalPorDni(String dni);
        Optional<Usuario> buscarPorDni(String dni);

        Usuario actualizar(Long id, UsuarioEditDTO dto, MultipartFile imagen) throws Exception; // edición

        List<UsuarioResponseDTO> listarTodos();
        UsuarioDetalleDTO obtenerDetalle(Long id) throws Exception;
        void pausar(Long id) throws Exception;
        List<UsuarioResponseDTO> buscarPorDniNombreOApellido(String filtro);



    }
