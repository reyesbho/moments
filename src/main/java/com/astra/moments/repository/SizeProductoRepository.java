package com.astra.moments.repository;

import com.astra.moments.model.SizeProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SizeProductoRepository extends JpaRepository<SizeProducto, Long> {

    Optional<SizeProducto> findByClave(String clave);
}
