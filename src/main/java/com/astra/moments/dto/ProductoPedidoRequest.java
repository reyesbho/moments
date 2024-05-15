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


    private Long idProducto;
    private Long idSabor;
    private Long idTipoProducto;
    private String texto;
    private String comentarios;
    private Integer porciones;
    private Integer precio;
}
