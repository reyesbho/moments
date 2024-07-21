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

    private Long idProducto;
    private Long idSize;
    private Long idTipoCobro;
    private String descripcion;
    private Float precio;
    private String imagen;
    private String comentarios;

}
