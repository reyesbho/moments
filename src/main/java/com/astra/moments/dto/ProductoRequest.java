package com.astra.moments.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
public class ProductoRequest {
    private long id;
    @NotBlank
    @NotNull
    private String clave;
    @NotBlank
    @NotNull
    private String descripcion;
    private Boolean estatus;
    private String imagen;
    private boolean isCompleted;
}
