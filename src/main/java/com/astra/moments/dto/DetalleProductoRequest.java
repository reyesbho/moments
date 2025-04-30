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
public class DetalleProductoRequest {

    @NotNull
    private Long idProducto;
    @NotNull
    private Long idSize;
    private Long idSabor;
    private Long idTipoProducto;
    private String descripcion;
    @NotNull
    private Float precio;
    private String imagen;
    private String comentarios;

}
