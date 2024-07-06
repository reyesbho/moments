package com.astra.moments.repository;

import com.astra.moments.model.Sabor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SaborRepository extends JpaRepository<Sabor, Long> {
    Optional<Sabor> findByClave(String clave);
}
