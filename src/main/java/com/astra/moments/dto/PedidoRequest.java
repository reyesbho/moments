package com.astra.moments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRequest {

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date fechaEntrega;
    private String lugarEntrega;
    private Float total;
    private ClienteResponse cliente;
    private List<ProductoPedidoRequest> productos;
    private Long idPedido;
}
