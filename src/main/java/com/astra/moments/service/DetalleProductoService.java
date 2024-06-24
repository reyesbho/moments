package com.astra.moments.service;

import com.astra.moments.dto.DetalleProductoResponse;
import com.astra.moments.model.DetalleProducto;
import com.astra.moments.repository.DetalleProductoRepository;
import com.astra.moments.util.MapObject;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DetalleProductoService {

    private DetalleProductoRepository detalleProductoRepository;

    DetalleProductoService( DetalleProductoRepository detalleProductoRepository){
        this.detalleProductoRepository = detalleProductoRepository;
    }

    @ReadOnlyProperty
    public Page<DetalleProductoResponse> getDetalleProductos(Optional<String> estatus, Pageable pageRequest){
        Page<DetalleProducto> detalleProductosPage = null;
        Page<DetalleProductoResponse> detalleProductoResponse = null;

        if (estatus.isPresent()){
            detalleProductosPage = this.detalleProductoRepository.findByEstatus(estatus.get(), pageRequest);
        } else {
            detalleProductosPage = this.detalleProductoRepository.findAll(pageRequest);
        }
        return new PageImpl<>(detalleProductosPage.stream().map(MapObject::mapToDetalleProductoResponse).toList(), pageRequest, detalleProductosPage.getTotalElements());
    }
}
