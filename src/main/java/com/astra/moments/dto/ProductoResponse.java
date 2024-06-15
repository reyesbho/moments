package com.astra.moments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
public class ProductoResponse {
    private Long id;
    private String clave;
    private String descripcion;
    private String estatus;
    private String imagen;
    private boolean cobroUnidad;
}
