package com.romacontrol.romacontrol.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuotaMensual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion; // Ej: Julio
    private Integer anio;
    private Double importe;

    private LocalDate fechaLimitePago;

    private Boolean activa;

    @OneToMany(mappedBy = "cuotaMensual", cascade = CascadeType.ALL)
    private List<Pago> pagos;
}
