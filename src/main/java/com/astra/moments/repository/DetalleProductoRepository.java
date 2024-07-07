package com.astra.moments.repository;

import com.astra.moments.model.DetalleProducto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleProductoRepository extends JpaRepository<DetalleProducto, Long> {

    Page<DetalleProducto> findByEstatus(String estatus, Pageable pageable);

    List<DetalleProducto> findByProductoId(Long idProducto);
}
