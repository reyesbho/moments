package com.astra.moments.dto;

import jakarta.persistence.Column;
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
    private Long idSabor;
    private Long idTipoProducto;
    private String comentarios;
    private Long cantidad;
    private Float total;
    private Float descuento;
}
