package com.astra.moments.controller;

import com.astra.moments.dto.TipoProductoRequest;
import com.astra.moments.dto.TipoProductoResponse;
import com.astra.moments.service.TipoProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipoProducto")
public class TipoProductoController {

    private TipoProductoService tipoProductoService;

    public TipoProductoController(TipoProductoService tipoProductoService){
        this.tipoProductoService = tipoProductoService;
    }

    @GetMapping("")
    public ResponseEntity getTipoProductos(){
        List<TipoProductoResponse> saborResponseList = this.tipoProductoService.getTipoProducto();
        return  new ResponseEntity<>(saborResponseList, HttpStatus.OK);
    }

    @DeleteMapping("/{idTipoProducto}")
    public ResponseEntity deleteTipoProducto(@PathVariable("idTipoProducto") Long idTipoProducto){
        this.tipoProductoService.deleteTipoProducto(idTipoProducto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{idTipoProducto}/{estatus}")
    public ResponseEntity<TipoProductoResponse> updateStatus(@PathVariable("idTipoProducto") Long idTipoProducto, @PathVariable("estatus") boolean estatus){
        TipoProductoResponse tipoProductoResponse = this.tipoProductoService.updateStatus(idTipoProducto, estatus);
        return new ResponseEntity<>(tipoProductoResponse, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<TipoProductoResponse> createTipoProducto(@RequestBody @Validated TipoProductoRequest tipoProductoRequest){
        return new ResponseEntity<>(this.tipoProductoService.addTipoProducto(tipoProductoRequest), HttpStatus.CREATED);
    }
}
