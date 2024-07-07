package com.astra.moments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoCobroRequest {
    private Long id;
    private String clave;
    private String descripcion;

}
