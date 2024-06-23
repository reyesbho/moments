package com.astra.moments.repository;

import com.astra.moments.model.TipoCobro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleProductoRepository extends JpaRepository<TipoCobro, Long> {
}
