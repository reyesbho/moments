package com.astra.moments.dto;

import jakarta.persistence.*;
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
    private String estatus;


    public TipoProductoResponse(Long idProducto){
        this.id = idProducto;
    }
}