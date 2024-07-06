package com.astra.moments.repository;

import com.astra.moments.model.TipoCobro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoCobroRepository extends JpaRepository<TipoCobro, Long> {
    Optional<TipoCobro> findByClave(String clave);
}
