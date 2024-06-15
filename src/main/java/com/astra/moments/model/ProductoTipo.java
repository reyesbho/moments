package com.astra.moments.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cat_producto_tipo")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoTipo {
    @Id
    @Column(name = "id_tipo_producto")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "clave")
    private String clave;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "estatus")
    private String estatus;

    @Column(name = "id_producto")
    private Long idProducto;

    public ProductoTipo(Long idProducto){
        this.id = idProducto;
    }
}
