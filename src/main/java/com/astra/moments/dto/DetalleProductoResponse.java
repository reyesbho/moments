package com.astra.moments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetalleProductoResponse {

    private Long id;
    private ProductoResponse producto;
    private TamanoProductoResponse size;
    private SaborResponse sabor;
    private TipoProductoResponse tipoProducto;
    private TipoCobroResponse tipoCobro;
    private String descripcion;
    private String estatus;
    private String precio;

    public DetalleProductoResponse(Long id){
        this.id = id;
    }
}
