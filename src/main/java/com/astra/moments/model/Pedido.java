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
@Table(name = "pedido")
public class Pedido {
    @Id
    @Column(name = "id_pedido")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_entrega", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEntrega;

    @Column(name = "lugar_entrega", nullable = false)
    private String lugarEntrega;

    @Column(name = "estatus", nullable = false)
    private String estatus;

    @Column(name = "estatus_pago", nullable = false)
    private String estatusPago;

    @Column(name = "total", nullable = false)
    private Float total;

    @Column(name = "fecha_registro", nullable = false)
    private Date fechaRegistro;

    @Column(name = "fecha_actualizacion")
    private Date fechaActualizacion;

    @Column(name = "registrado_por")
    private String registradoPor;

    @OneToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    private Cliente cliente;


    public Pedido(Long id){
        this.id = id;
    }

}
