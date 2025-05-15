package com.astra.moments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponse  implements Serializable {

    private Long id;
    private Date fechaEntrega;
    private String lugarEntrega;
    private String estatus;
    private String estatusPago;
    private Float total;
    private Date fechaRegistro;
    private Date fechaActualizacion;
    private ClienteResponse cliente;
    private String registradoPor;
}
