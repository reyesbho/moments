package com.astra.moments.controller;

import com.astra.moments.dto.ProductoRequest;
import com.astra.moments.dto.ProductoResponse;
import com.astra.moments.dto.SaborRequest;
import com.astra.moments.dto.SaborResponse;
import com.astra.moments.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{idProducto}")
    public ResponseEntity deleteProducto(@PathVariable("idProducto") Long idProducto){
        this.productoService.deleteProducto(idProducto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{idProducto}/{estatus}")
    public ResponseEntity<ProductoResponse> updateStatus(@PathVariable("idProducto") Long idSabor, @PathVariable("estatus") boolean estatus){
        ProductoResponse productoResponse = this.productoService.updateStatus(idSabor, estatus);
        return new ResponseEntity<>(productoResponse, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ProductoResponse> addProducto(@RequestBody ProductoRequest productoRequest){
        return new ResponseEntity<>(this.productoService.addProducto(productoRequest),HttpStatus.CREATED);
    }

    @GetMapping("/{id}/producto")
    public ResponseEntity getDetailProducts(@PathVariable("id") Long idProducto){
        return new ResponseEntity(this.productoService.getDetailProductsByProducto(idProducto), HttpStatus.OK);
    }

}
