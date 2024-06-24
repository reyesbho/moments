package com.astra.moments.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cat_size")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TamanoProducto {
    @Id
    @Column(name = "id_size")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "clave", nullable = false)
    private String clave;
    @Column(name = "descripcion", nullable = false)
    private String descripcion;
    @Column(name = "estatus", nullable = false)
    private Boolean estatus;

    public TamanoProducto(Long id){
        this.id = id;
    }
}
