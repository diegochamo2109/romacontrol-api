package com.romacontrol.romacontrol.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "cuota_mensual_id", nullable = false)
    private CuotaMensual cuotaMensual;

    private String estadoPago;

    private String formaPago;

    private LocalDateTime fechaPago;

    @ManyToOne
    @JoinColumn(name = "registrado_por_id", nullable = false)
    private Usuario registradoPor;

    private Double monto;
}
