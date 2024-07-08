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
    @JoinColumn(name = "id_detalle_producto", referencedColumnName = "id_detalle_producto")
    private DetalleProducto detalleProducto;

    @Column(name = "comentarios")
    private String comentarios;

    @Column(name = "cantidad", nullable = false)
    private Long cantidad;

    @Column(name = "fecha_registro", nullable = false)
    private Date fechaRegistro;

    @Column(name = "fecha_actualizacion")
    private Date fechaActualizacion;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sabor", referencedColumnName = "id_sabor", nullable = true)
    private Sabor sabor;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tipo_producto", referencedColumnName = "id_tipo_producto", nullable = false)
    private TipoProducto tipoProducto;
    @Column(name = "total", nullable = false)
    private Float total;
    @Column(name = "descuento", nullable = false)
    private Float descuento;
}
