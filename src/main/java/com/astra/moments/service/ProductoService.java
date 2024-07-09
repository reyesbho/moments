package com.astra.moments.service;

import com.astra.moments.dto.*;
import com.astra.moments.exception.EntityExistException;
import com.astra.moments.exception.EntityNotFoundException;
import com.astra.moments.model.DetalleProducto;
import com.astra.moments.model.Producto;
import com.astra.moments.model.Sabor;
import com.astra.moments.repository.DetalleProductoRepository;
import com.astra.moments.repository.ProductoRepository;
import com.astra.moments.util.MapObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private ProductoRepository productoRepository;
    private DetalleProductoRepository detalleProductoRepository;

    public ProductoService(ProductoRepository productoRepository, DetalleProductoRepository detalleProductoRepository){
        this.productoRepository = productoRepository;
        this.detalleProductoRepository = detalleProductoRepository;
    }


    public List<ProductoResponse> getProductos(){
        List<Producto> productos = this.productoRepository.findAll();
        return productos.stream().map(MapObject::mapToProductResponse).toList();
    }

    public ProductoResponse getProducto(Long id){
        Optional<Producto> optionalProducto = this.productoRepository.findById(id);
        return optionalProducto.map(MapObject::mapToProductResponse).orElse(null);
    }

    @Transactional
    public void deleteProducto(Long idProducto){
        Optional<Producto> optionalProducto = this.productoRepository.findById(idProducto);
        if (optionalProducto.isEmpty()){
            throw new EntityNotFoundException("Error al buscar el producto");
        }
        try {
            this.productoRepository.delete(optionalProducto.get());
        }catch (Exception e){
            throw  new RuntimeException("Error al eliminar el producto");
        }
    }

    @Transactional
    public ProductoResponse updateStatus(Long idPedido, Boolean status){
        Optional<Producto> optionalProducto = this.productoRepository.findById(idPedido);
        if (optionalProducto.isEmpty()){
            throw new EntityNotFoundException("Error al buscar el producto");
        }
        Producto producto = optionalProducto.get();
        producto.setEstatus(status);
        this.productoRepository.save(producto);
        return MapObject.mapToProductResponse(producto);
    }

    @Transactional
    public ProductoResponse addProducto(ProductoRequest productoRequest){
        Optional<Producto> optionalProducto = this.productoRepository.findByClave(productoRequest.getClave());
        if (optionalProducto.isPresent()){
            throw  new EntityExistException("El producto ya existe");
        }
        Producto producto = Producto.builder()
                .clave(productoRequest.getClave())
                .descripcion(productoRequest.getDescripcion())
                .estatus(Boolean.TRUE)
                .imagen(productoRequest.getImagen())
                .build();

        this.productoRepository.save(producto);
        return MapObject.mapToProductResponse(producto);
    }

    public List<DetalleProductoResponse> getDetailProductsByProducto(Long idProducto){
        List<DetalleProducto> detailProductos = this.detalleProductoRepository.findByProductoId(idProducto);
        return detailProductos.stream().map(MapObject::mapToDetalleProductoResponse).toList();
    }


}
