package com.astra.moments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRequest {

    private Long idPedido;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private Date fechaEntrega;
    private String lugarEntrega;
    private ClienteResponse cliente;
}
