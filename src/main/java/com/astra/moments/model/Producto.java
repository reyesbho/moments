package com.astra.moments.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cat_producto", schema = "public")
public class Producto {


    @Id
    @Column(name = "id_producto")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "clave", nullable = false)
    private String clave;
    @Column(name = "descripcion" , nullable = false)
    private String descripcion;
    @Column(name = "estatus" , nullable = false)
    private Boolean estatus;
    @Column(name = "imagen" , nullable = false)
    private String imagen;

    public Producto(Long idProducto){
        this.id = idProducto;
    }
}
