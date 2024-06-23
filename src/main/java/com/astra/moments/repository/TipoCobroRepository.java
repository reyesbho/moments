package com.astra.moments.repository;

import com.astra.moments.model.TamanoProducto;
import com.astra.moments.model.TipoCobro;
import com.astra.moments.model.TipoProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoCobroRepository extends JpaRepository<TipoCobro, Long> {
}
