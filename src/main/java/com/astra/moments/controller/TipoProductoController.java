package com.astra.moments.controller;

import com.astra.moments.dto.TipoProductoResponse;
import com.astra.moments.service.TipoProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return  new ResponseEntity(saborResponseList, HttpStatus.OK);
    }
}
