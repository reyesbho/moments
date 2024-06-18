package com.astra.moments.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "pedido_producto")
public class ProductoPedido {
    @Id
    @Column(name = "id_pedido_producto")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_pedido", nullable = false)
    private Long idPedido;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_producto", referencedColumnName = "id_producto")
    private Producto producto;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sabor", referencedColumnName = "id_sabor")
    private Sabor sabor;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tipo_producto", referencedColumnName = "id_tipo_producto")
    private ProductoTipo tipoProducto;

    @Column(name = "texto")
    private String texto;

    @Column(name = "comentarios")
    private String comentarios;

    @Column(name = "fecha_registro", nullable = false)
    private Date fechaRegistro;

    @Column(name = "fecha_actualizacion")
    private Date fechaActualizacion;

    @Column(name = "size")
    private Integer size;

    @Column(name= "precio")
    private Integer precio;
}
