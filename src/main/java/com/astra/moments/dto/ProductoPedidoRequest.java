package com.astra.moments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoPedidoRequest {


    private Long idDetalleProducto;
    private String comentarios;
    private Long cantidad;
}
