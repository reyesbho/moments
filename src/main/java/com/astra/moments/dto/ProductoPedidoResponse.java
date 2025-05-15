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
public class ProductoPedidoResponse {
    private Long id;
    private Long idPedido;
    private SizeProductoResponse sizeProducto;
    private ProductoResponse producto;
    private String caracteristicas;
    private Long cantidad;
    private Date fechaRegistro;
    private Date fechaActualizacion;
    private Float precio;
}
