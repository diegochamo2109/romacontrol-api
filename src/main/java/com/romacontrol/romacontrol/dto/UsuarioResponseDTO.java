
    package com.romacontrol.romacontrol.dto;

    import java.time.LocalDateTime;
    import java.util.List;

    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    // 🔹 NUEVO: DTO para responder datos al listar usuarios
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class UsuarioResponseDTO {
        private Long id;
        private String dni;
        private String nombreCompleto;
        private List<String> roles;
        private boolean activo;
        private LocalDateTime fechaCreacion;
        private String creadoPor;
    }
