package com.astra.moments.service;

import com.astra.moments.dto.DetalleProductoRequest;
import com.astra.moments.dto.DetalleProductoResponse;
import com.astra.moments.exception.EntityNotFoundException;
import com.astra.moments.model.DetalleProducto;
import com.astra.moments.model.Producto;
import com.astra.moments.model.SizeProducto;
import com.astra.moments.model.TipoCobro;
import com.astra.moments.repository.DetalleProductoRepository;
import com.astra.moments.repository.ProductoRepository;
import com.astra.moments.repository.SizeProductoRepository;
import com.astra.moments.repository.TipoCobroRepository;
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
    private TipoCobroRepository tipoCobroRepository;
    private SizeProductoRepository sizeProductoRepository;

    DetalleProductoService( DetalleProductoRepository detalleProductoRepository,
                            ProductoRepository productoRepository,
                            TipoCobroRepository tipoCobroRepository,
                            SizeProductoRepository sizeProductoRepository){
        this.detalleProductoRepository = detalleProductoRepository;
        this.productoRepository = productoRepository;
        this.tipoCobroRepository = tipoCobroRepository;
        this.sizeProductoRepository = sizeProductoRepository;
    }

    @ReadOnlyProperty
    public Page<DetalleProductoResponse> getDetalleProductos(Optional<String> estatus, Pageable pageRequest){
        Page<DetalleProducto> detalleProductosPage = null;

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
        Producto producto = this.productoRepository.findById(detalleProductoRequest.getIdProducto())
                .orElseThrow(() -> new EntityNotFoundException("Error al validar el producto"));
        //validate tipo cobro
        TipoCobro tipoCobro = this.tipoCobroRepository.findById(detalleProductoRequest.getIdTipoCobro())
                .orElseThrow(() -> new EntityNotFoundException("Error al validar el tipo cobro"));
        //validate size
        SizeProducto tamano = this.sizeProductoRepository.findById(detalleProductoRequest.getIdSize())
                .orElseThrow(() -> new EntityNotFoundException("Error al validar el tamaño"));

        DetalleProducto detalleProducto = DetalleProducto.builder()
                .producto(producto)
                .size(tamano)
                .tipoCobro(tipoCobro)
                .descripcion(detalleProductoRequest.getDescripcion())
                .estatus(Boolean.TRUE)
                .precio(detalleProductoRequest.getPrecio())
                .imagen(detalleProductoRequest.getImagen())
                .fechaRegistro(new Date())
                .fechaActualizacion(null)
                .comentarios(detalleProductoRequest.getComentarios())
                .build();
        this.detalleProductoRepository.save(detalleProducto);
        return MapObject.mapToDetalleProductoResponse(detalleProducto);
    }


    @Transactional
    public void deleteDetalleProducto(Long idDetalleProducto){
        Optional<DetalleProducto> optionalDetalleProducto = this.detalleProductoRepository.findById(idDetalleProducto);
        if (optionalDetalleProducto.isEmpty()){
            throw new EntityNotFoundException("Error al buscar el detalle del producto");
        }
        try{
            this.detalleProductoRepository.delete(optionalDetalleProducto.get());
        }catch (Exception e){
            throw new RuntimeException("Error al eliminar el detalle del producto");
        }
    }

    @Transactional
    public DetalleProductoResponse updateStatus(Long idDetalleProducto, Boolean status){
        Optional<DetalleProducto> optionalDetalleProducto = this.detalleProductoRepository.findById(idDetalleProducto);
        if (optionalDetalleProducto.isEmpty()){
            throw new EntityNotFoundException("Error al buscar el detalle del producto");
        }
        DetalleProducto detalleProducto = optionalDetalleProducto.get();
        detalleProducto.setEstatus(status);
        this.detalleProductoRepository.save(detalleProducto);
        return MapObject.mapToDetalleProductoResponse(detalleProducto);
    }
}
