package com.astra.moments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetalleProductoRequest {

    private Long producto;
    private Long size;
    private Long sabor;
    private Long tipoProducto;
    private Long tipoCobro;
    private String descripcion;
    private String estatus;
    private Float precio;

}
