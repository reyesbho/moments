package com.astra.moments.service;

import com.astra.moments.dto.ProductoResponse;
import com.astra.moments.model.Producto;
import com.astra.moments.repository.ProductoRepository;
import com.astra.moments.util.MapObject;
import org.springframework.stereotype.Service;

import java.util.List;
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

}
