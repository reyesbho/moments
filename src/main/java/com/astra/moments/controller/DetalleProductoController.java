package com.astra.moments.controller;

import com.astra.moments.dto.DetalleProductoResponse;
import com.astra.moments.service.DetalleProductoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/detalleProducto")
public class DetalleProductoController {
    private DetalleProductoService detalleProductoService;

    DetalleProductoController(DetalleProductoService detalleProductoService){
        this.detalleProductoService = detalleProductoService;
    }

    @GetMapping("/detailProducts")
    public ResponseEntity getDetalleProductos(@RequestParam(name = "estatus", required = false) Optional<String> estatus,
                                              @RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                              @RequestParam(name = "size",  defaultValue = "10", required = false) int size){
        Pageable pageRequest = PageRequest.of(page, size);
        Page<DetalleProductoResponse> productos = this.detalleProductoService.getDetalleProductos(estatus, pageRequest);
        return new ResponseEntity(productos, HttpStatus.OK);
    }

}
