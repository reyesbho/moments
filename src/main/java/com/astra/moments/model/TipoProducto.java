package com.astra.moments.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cat_tipo_producto")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoProducto {
    @Id
    @Column(name = "id_tipo_producto")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "clave")
    private String clave;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "estatus")
    private Boolean estatus;

    @Column(name = "tags")
    private String tags;


    public TipoProducto(Long idProducto){
        this.id = idProducto;
    }
}
