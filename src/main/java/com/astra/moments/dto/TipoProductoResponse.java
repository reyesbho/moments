package com.astra.moments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoProductoResponse {
    private Long id;
    private String clave;
    private String descripcion;
    private Boolean estatus;
    private String tags;

    public TipoProductoResponse(Long idProducto){
        this.id = idProducto;
    }
}
