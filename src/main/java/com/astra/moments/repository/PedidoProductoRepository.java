package com.astra.moments.repository;

import com.astra.moments.model.ProductoPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoProductoRepository extends JpaRepository<ProductoPedido, Long> {
    List<ProductoPedido> findByIdPedido(Long idPedido);
}
