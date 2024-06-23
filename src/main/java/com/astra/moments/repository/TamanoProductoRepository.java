package com.astra.moments.repository;

import com.astra.moments.model.Sabor;
import com.astra.moments.model.TamanoProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TamanoProductoRepository extends JpaRepository<TamanoProducto, Long> {
}
