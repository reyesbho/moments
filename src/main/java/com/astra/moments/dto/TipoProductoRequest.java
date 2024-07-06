package com.astra.moments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoProductoRequest {
    private Long id;
    private String clave;
    private String descripcion;

}
