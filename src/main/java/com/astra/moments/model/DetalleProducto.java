package com.astra.moments.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cat_detalle_producto", schema = "public")
public class DetalleProducto {


    @Id
    @Column(name = "id_detalle_producto")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_producto", referencedColumnName = "id_producto", nullable = false)
    private Producto producto;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_size", referencedColumnName = "id_size", nullable = false)
    private SizeProducto size;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tipo_cobro", referencedColumnName = "id_tipo_cobro", nullable = false)
    private TipoCobro tipoCobro;
    @Column(name = "descripcion" , nullable = false)
    private String descripcion;
    @Column(name = "estatus" , nullable = false)
    private Boolean estatus;
    @Column(name = "precio" , nullable = false)
    private Float precio;
    @Column(name = "fecha_registro", nullable = false)
    private Date fechaRegistro;
    @Column(name = "fecha_actualizacion")
    private Date fechaActualizacion;

    public DetalleProducto(Long id){
        this.id = id;
    }
}
