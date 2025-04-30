package com.astra.moments.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteRequest {
    @NotBlank
    @NotNull
    private String nombre;
    @NotBlank
    @NotNull
    private String apellidoPaterno;
    private String apellidoMaterno;
    @NotBlank
    @NotNull
    private String direccion;
}
