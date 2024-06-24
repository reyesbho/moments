package com.astra.moments.controller;

import com.astra.moments.dto.ProductoResponse;
import com.astra.moments.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/producto")

public class ProductoController {
    private ProductoService productoService;
    public ProductoController(ProductoService productoService){
        this.productoService = productoService;
    }


    @GetMapping("")
    public ResponseEntity getProductos(){
        List<ProductoResponse> productos = this.productoService.getProductos();
        return new ResponseEntity(productos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getProducto(@PathVariable("id") Long id){
        return new ResponseEntity(this.productoService.getProducto(id), HttpStatus.OK);
    }

}
