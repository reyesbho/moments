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
    private TamanoProducto size;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sabor", referencedColumnName = "id_sabor", nullable = true)
    private Sabor sabor;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tipo_producto", referencedColumnName = "id_tipo_producto", nullable = false)
    private TipoProducto tipoProducto;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tipo_cobro", referencedColumnName = "id_tipo_cobro", nullable = false)
    private TipoCobro tipoCobro;
    @Column(name = "descripcion" , nullable = false)
    private String descripcion;
    @Column(name = "estatus" , nullable = false)
    private String estatus;
    @Column(name = "precio" , nullable = false)
    private String precio;

    public DetalleProducto(Long id){
        this.id = id;
    }
}
