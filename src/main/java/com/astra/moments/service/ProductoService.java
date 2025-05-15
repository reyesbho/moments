package com.astra.moments.service;

import com.astra.moments.dto.ProductoRequest;
import com.astra.moments.dto.ProductoResponse;
import com.astra.moments.exception.EntityExistException;
import com.astra.moments.exception.EntityNotFoundException;
import com.astra.moments.model.Producto;
import com.astra.moments.repository.ProductoRepository;
import com.astra.moments.util.MapObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductoService {

    private ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository){
        this.productoRepository = productoRepository;
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
                .isCompleted(productoRequest.isCompleted())
                .build();

        this.productoRepository.save(producto);
        return MapObject.mapToProductResponse(producto);
    }


    @Transactional
    public ProductoResponse updateProducto(ProductoRequest productoRequest){
        if (Objects.isNull(productoRequest.getId()))
            throw  new EntityExistException("El producto no existe");

        Optional<Producto> optionalProducto = this.productoRepository.findById(productoRequest.getId());
        if (!optionalProducto.isPresent()){
            throw  new EntityExistException("El producto no existe");
        }
        Producto producto = optionalProducto.get();
        producto.setClave(productoRequest.getClave());
        producto.setDescripcion(productoRequest.getDescripcion());
        producto.setImagen(productoRequest.getImagen());

        this.productoRepository.save(producto);
        return MapObject.mapToProductResponse(producto);
    }


}
