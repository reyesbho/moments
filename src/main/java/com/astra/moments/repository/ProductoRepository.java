package com.astra.moments.repository;

import com.astra.moments.model.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Page<Producto> findByEstatus(String estatus, Pageable pageable);
    Optional<Producto> findByClave(String clave);
}
