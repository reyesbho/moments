package com.astra.moments.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cc_rol")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rol {
    @Id
    @Column(name = "id_rol")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "rol", nullable = false)
    private String rol;
    @Column(name = "descripcion", nullable = false)
    private String descripcion;


    public Rol(Long idRol){
        this.id = idRol;
    }
}
