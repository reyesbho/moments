package com.astra.moments.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoProductoRequest {
    @NotBlank
    @NotNull
    private String clave;
    @NotBlank
    @NotNull
    private String descripcion;
    private String tags;

}
