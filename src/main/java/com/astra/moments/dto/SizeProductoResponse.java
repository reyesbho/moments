package com.astra.moments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SizeProductoResponse {
    private Long id;
    private String clave;
    private String descripcion;
    private Boolean estatus;

    public SizeProductoResponse(Long id){
        this.id = id;
    }
}
