package com.astra.moments.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cat_tipo_cobro")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TipoCobro {
    @Id
    @Column(name = "id_tipo_cobro")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "clave", nullable = false)
    private String clave;
    @Column(name = "descripcion", nullable = false)
    private String descripcion;
    @Column(name = "estatus", nullable = false)
    private String estatus;

    public TipoCobro(Long id){
        this.id = id;
    }
}
