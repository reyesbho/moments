package com.astra.moments.service;

import com.astra.moments.dto.DetalleProductoResponse;
import com.astra.moments.dto.ProductoResponse;
import com.astra.moments.model.DetalleProducto;
import com.astra.moments.model.Producto;
import com.astra.moments.repository.DetalleProductoRepository;
import com.astra.moments.repository.ProductoRepository;
import com.astra.moments.util.MapObject;
import org.springframework.stereotype.Service;

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

    public List<DetalleProductoResponse> getDetailProductsByProducto(Long idProducto){
        List<DetalleProducto> detailProductos = this.detalleProductoRepository.findByProductoId(idProducto);
        return detailProductos.stream().map(MapObject::mapToDetalleProductoResponse).toList();
    }


}
