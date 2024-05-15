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
    private ProductoResponse producto;
    private SaborResponse sabor;
    private ProductoTipoResponse tipoProducto;
    private String texto;
    private String comentarios;
    private Date fechaRegistro;
    private Date fechaActualizacion;
    private Integer porciones;
    private Integer precio;
}
