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
public class PedidoProducto {
    @Id
    @Column(name = "id_pedido_producto")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pedido", referencedColumnName = "id_pedido")
    private Pedido pedido;

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

    @Column(name = "sub_total", nullable = false)
    private Float subTotal;
    @Column(name = "descuento", nullable = false)
    private Float descuento;
}
