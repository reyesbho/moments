package com.astra.moments.service;

import com.astra.moments.dto.ProductoResponse;
import com.astra.moments.dto.ProductoTipoResponse;
import com.astra.moments.model.Producto;
import com.astra.moments.model.TipoProducto;
import com.astra.moments.repository.ProductoRepository;
import com.astra.moments.repository.TipoProductoRepository;
import com.astra.moments.util.MapObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    private ProductoRepository productoRepository;
    private TipoProductoRepository tipoProductoRepository;

    public ProductoService(ProductoRepository productoRepository, TipoProductoRepository tipoProductoRepository){
        this.productoRepository = productoRepository;
        this.tipoProductoRepository = tipoProductoRepository;
    }

    public Page<ProductoResponse> getProductos(Optional<String> estatus, Pageable pageRequest){
        Page<Producto> productosPage = null;
        Page<ProductoResponse> productoResponse = null;

        if (estatus.isPresent()){
            productosPage = this.productoRepository.findByEstatus(estatus.get(), pageRequest);
        } else {
            productosPage = this.productoRepository.findAll(pageRequest);
        }
        List<Producto> productos = productosPage.getContent();
        productoResponse = new PageImpl<>(productos.stream().map(MapObject::mapToProductResponse).collect(Collectors.toList()),pageRequest,productosPage.getTotalElements());
        return productoResponse;
    }

    public ProductoResponse getProducto(Long id){
        Optional<Producto> optionalProducto = this.productoRepository.findById(id);
        return optionalProducto.map(MapObject::mapToProductResponse).orElse(null);
    }

    public List<ProductoTipoResponse> getProductTipo(Long id){
        List<TipoProducto> tipoProductoList = this.tipoProductoRepository.findByIdProducto(id);
        return tipoProductoList.stream().map(MapObject::mapToProductoTipoResponse).collect(Collectors.toList());
    }


}
