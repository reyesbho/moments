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
    private Long idProducto;
    @NotNull
    private Long idSize;
    private String caracteristicas;
    private Long cantidad;
    private Float precio;
}
