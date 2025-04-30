package com.astra.moments.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoPedidoRequest {
    @NotNull
    private Long idDetalleProducto;
    private String comentarios;
    private Long cantidad;
    private Float subTotal;
    private Float descuento;
}
