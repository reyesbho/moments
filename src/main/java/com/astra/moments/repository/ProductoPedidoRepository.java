package com.astra.moments.repository;

import com.astra.moments.model.PedidoProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoPedidoRepository extends JpaRepository<PedidoProducto, Long> {
    List<PedidoProducto> findByPedidoId(Long idPedido);
}
