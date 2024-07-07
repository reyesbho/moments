package com.astra.moments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetalleProductoResponse {

    private Long id;
    private ProductoResponse producto;
    private SizeProductoResponse size;
    private SaborResponse sabor;
    private TipoProductoResponse tipoProducto;
    private TipoCobroResponse tipoCobro;
    private String descripcion;
    private Boolean estatus;
    private Float precio;
    private Date fechaRegistro;
    private Date fechaActualizacion;

    public DetalleProductoResponse(Long id){
        this.id = id;
    }
}
