package com.astra.moments.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cat_sabor")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sabor {
    @Id
    @Column(name = "id_sabor")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "clave", nullable = false)
    private String clave;
    @Column(name = "descripcion", nullable = false)
    private String descripcion;
    @Column(name = "estatus", nullable = false)
    private Boolean estatus;

    public Sabor(Long idSabor){
        this.id = idSabor;
    }
}
