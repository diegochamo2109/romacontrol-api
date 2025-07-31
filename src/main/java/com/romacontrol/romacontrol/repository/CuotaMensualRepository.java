package com.romacontrol.romacontrol.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.romacontrol.romacontrol.model.CuotaMensual;

public interface CuotaMensualRepository extends JpaRepository<CuotaMensual, Long> {

    List<CuotaMensual> findByActivaTrue();

    @Query("SELECT c FROM CuotaMensual c WHERE c.activa = true AND c.fechaLimitePago > :fechaActual ORDER BY c.fechaLimitePago ASC")
    Optional<CuotaMensual> findProximaCuotaActiva(LocalDate fechaActual);
    

    @Query("SELECT c FROM CuotaMensual c WHERE c NOT IN (" +
       "SELECT p.cuotaMensual FROM Pago p WHERE p.usuario.id = :usuarioId AND p.estadoPago = 'Pagado')")
List<CuotaMensual> findCuotasImpagasPorUsuarioId(@Param("usuarioId") Long usuarioId);

}
