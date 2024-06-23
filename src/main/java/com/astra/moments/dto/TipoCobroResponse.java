package com.astra.moments.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TipoCobroResponse {
    private Long id;
    private String clave;
    private String descripcion;
    private String estatus;

    public TipoCobroResponse(Long id){
        this.id = id;
    }
}