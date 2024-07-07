package com.astra.moments.service;

import com.astra.moments.dto.DetalleProductoRequest;
import com.astra.moments.dto.DetalleProductoResponse;
import com.astra.moments.exception.EntityNotFoundException;
import com.astra.moments.model.*;
import com.astra.moments.repository.*;
import com.astra.moments.util.MapObject;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class DetalleProductoService {

    private DetalleProductoRepository detalleProductoRepository;
    private ProductoRepository productoRepository;
    private SaborRepository saborRepository;
    private TipoCobroRepository tipoCobroRepository;
    private TipoProductoRepository tipoProductoRepository;
    private SizeProductoRepository sizeProductoRepository;

    DetalleProductoService( DetalleProductoRepository detalleProductoRepository,
                            ProductoRepository productoRepository,SaborRepository saborRepository,
                            TipoCobroRepository tipoCobroRepository,
                            TipoProductoRepository tipoProductoRepository,
                            SizeProductoRepository sizeProductoRepository){
        this.detalleProductoRepository = detalleProductoRepository;
        this.productoRepository = productoRepository;
        this.saborRepository = saborRepository;
        this.tipoCobroRepository = tipoCobroRepository;
        this.tipoProductoRepository = tipoProductoRepository;
        this.sizeProductoRepository = sizeProductoRepository;
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

    @Transactional
    public DetalleProductoResponse createDetalleProducto(DetalleProductoRequest detalleProductoRequest) throws Exception {
        //validate producto
        Producto producto = this.productoRepository.findById(detalleProductoRequest.getProducto())
                .orElseThrow(() -> new EntityNotFoundException("Error al validar el producto"));
        //validate sabor
        Sabor sabor =  this.saborRepository.findById(detalleProductoRequest.getSabor()).orElse(null);
        //validate tipoProducto
        TipoProducto tipoProducto = this.tipoProductoRepository.findById(detalleProductoRequest.getTipoProducto())
                .orElseThrow(() -> new EntityNotFoundException("Error al validar el tipo producto"));
        //validate tipo cobro
        TipoCobro tipoCobro = this.tipoCobroRepository.findById(detalleProductoRequest.getTipoCobro())
                .orElseThrow(() -> new EntityNotFoundException("Error al validar el tipo cobro"));
        //validate size
        SizeProducto tamano = this.sizeProductoRepository.findById(detalleProductoRequest.getSize())
                .orElseThrow(() -> new EntityNotFoundException("Error al validar el tamaño"));

        DetalleProducto detalleProducto = DetalleProducto.builder()
                .producto(producto)
                .size(tamano)
                .sabor(sabor)
                .tipoProducto(tipoProducto)
                .tipoCobro(tipoCobro)
                .descripcion(detalleProductoRequest.getDescripcion())
                .estatus(Boolean.TRUE)
                .precio(detalleProductoRequest.getPrecio())
                .fechaRegistro(new Date())
                .fechaActualizacion(null)
                .build();
        this.detalleProductoRepository.save(detalleProducto);
        return MapObject.mapToDetalleProductoResponse(detalleProducto);
    }
}
