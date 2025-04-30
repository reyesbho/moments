package com.astra.moments.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private long fechaEntrega;
    @NotNull
    @NotBlank
    private String lugarEntrega;
    @NotNull
    private ClienteResponse cliente;
}
