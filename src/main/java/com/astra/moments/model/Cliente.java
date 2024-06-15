package com.astra.moments.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cat_cliente")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cliente {
    @Id
    @Column(name = "id_cliente")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "apellido_paterno", nullable = false)
    private String apellidoPaterno;
    @Column(name = "apellido_materno", nullable = true)
    private String apellidoMaterno;
    @Column(name = "direccion", nullable = true)
    private String direccion;

    public Cliente(Long idCliente){
        this.id = idCliente;
    }
}
