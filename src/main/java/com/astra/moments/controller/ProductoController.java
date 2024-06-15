package com.astra.moments.controller;

import com.astra.moments.dto.ProductoResponse;
import com.astra.moments.dto.ProductoTipoResponse;
import com.astra.moments.service.ProductoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/producto")

public class ProductoController {
    private ProductoService productoService;
    public ProductoController(ProductoService productoService){
        this.productoService = productoService;
    }

    @GetMapping("")
    public ResponseEntity getProductos(@RequestParam(name = "estatus", required = false) Optional<String> estatus,
                                       @RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                       @RequestParam(name = "size",  defaultValue = "10", required = false) int size){
        Pageable pageRequest = PageRequest.of(page, size);
        Page<ProductoResponse> productos = this.productoService.getProductos(estatus, pageRequest);
        return new ResponseEntity(productos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getProducto(@PathVariable("id") Long id){
        return new ResponseEntity(this.productoService.getProducto(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/tipo")
    public ResponseEntity<List<ProductoTipoResponse>> getTipos(@PathVariable("id") Long id){
        List<ProductoTipoResponse> productoTipoResponseList = this.productoService.getProductTipo(id);
        return new ResponseEntity(productoTipoResponseList, HttpStatus.OK);
    }
}
