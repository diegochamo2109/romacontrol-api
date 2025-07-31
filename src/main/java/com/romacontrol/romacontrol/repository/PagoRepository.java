    package com.romacontrol.romacontrol.repository;

    import java.util.List;
    import java.util.Optional;

    import org.springframework.data.jpa.repository.JpaRepository;

    import com.romacontrol.romacontrol.model.Pago;
    import com.romacontrol.romacontrol.model.Usuario;

    public interface PagoRepository extends JpaRepository<Pago, Long> {

        List<Pago> findByUsuarioDniAndEstadoPago(String dni, String estadoPago);

        List<Pago> findByUsuarioId(Long usuarioId);
        
        Optional<Pago> findByUsuarioIdAndCuotaMensualIdAndEstadoPago(Long usuarioId, Long cuotaId, String estadoPago);

         List<Pago> findByUsuario(Usuario usuario);

    }
