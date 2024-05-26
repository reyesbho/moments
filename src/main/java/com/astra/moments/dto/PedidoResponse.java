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
public class PedidoResponse {

    private Long id;
    private Date fechaEntrega;
    private Date horaEntrega;
    private String lugarEntrega;
    private String estatus;
    private Float total;
    private Date fechaRegistro;
    private Date fechaActualizacion;
    private ClienteResponse cliente;
    private Integer numProductos;
    private String registradoPor;
}
