package com.astra.moments.repository;

import com.astra.moments.model.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Page<Pedido> findByEstatusAndFechaEntrega(String estatus, Date fechaEntrega, Pageable pageable);

    Page<Pedido> findByFechaEntrega(Date fechaEntrega, Pageable pageable);
}
