package com.astra.moments.controller;

import com.astra.moments.dto.DetalleProductoRequest;
import com.astra.moments.dto.DetalleProductoResponse;
import com.astra.moments.dto.SaborResponse;
import com.astra.moments.service.DetalleProductoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/detalleProducto")
public class DetalleProductoController {
    private DetalleProductoService detalleProductoService;

    DetalleProductoController(DetalleProductoService detalleProductoService){
        this.detalleProductoService = detalleProductoService;
    }

    @GetMapping("")
    public ResponseEntity getDetalleProductos(@RequestParam(name = "estatus", required = false) Optional<String> estatus,
                                              @RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                              @RequestParam(name = "size",  defaultValue = "10", required = false) int size){
        Pageable pageRequest = PageRequest.of(page, size);
        Page<DetalleProductoResponse> productos = this.detalleProductoService.getDetalleProductos(estatus, pageRequest);
        return new ResponseEntity(productos, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<DetalleProductoResponse> createDetalletProducto(@RequestBody DetalleProductoRequest detalleProductoRequest) throws Exception {
        DetalleProductoResponse response = this.detalleProductoService.createDetalleProducto(detalleProductoRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @DeleteMapping("/{idDetalleProducto}")
    public ResponseEntity deleteDetalleProducto(@PathVariable("idDetalleProducto") Long idDetalleProducto){
        this.detalleProductoService.deleteDetalleProducto(idDetalleProducto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{idDetalleProducto}/{estatus}")
    public ResponseEntity<DetalleProductoResponse> updateStatus(@PathVariable("idDetalleProducto") Long idDetalleProducto, @PathVariable("estatus") boolean estatus){
        DetalleProductoResponse detalleProductoResponse = this.detalleProductoService.updateStatus(idDetalleProducto, estatus);
        return new ResponseEntity<>(detalleProductoResponse, HttpStatus.OK);
    }
}
