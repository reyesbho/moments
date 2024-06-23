package com.astra.moments.controller;

import com.astra.moments.dto.*;
import com.astra.moments.service.CatalogoService;
import com.astra.moments.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/catalogo")
public class CatalogoController {

    private CatalogoService catalogService;
    private ClienteService clienteService;

    public CatalogoController(CatalogoService catalogService, ClienteService clienteService){
        this.catalogService = catalogService;
        this.clienteService = clienteService;
    }

    @GetMapping("/sabor")
    public ResponseEntity getSabores(){
        List<SaborResponse> saborResponseList = this.catalogService.getSabores();
        return  new ResponseEntity(saborResponseList, HttpStatus.OK);
    }

    @GetMapping("/size")
    public ResponseEntity getTamanos(){
        List<TamanoProductoResponse> saborResponseList = this.catalogService.getTamanos();
        return  new ResponseEntity(saborResponseList, HttpStatus.OK);
    }

    @GetMapping("/tipoCobro")
    public ResponseEntity getTipoCobros(){
        List<TipoCobroResponse> saborResponseList = this.catalogService.getTipoCobros();
        return  new ResponseEntity(saborResponseList, HttpStatus.OK);
    }

    @GetMapping("/tipoProducto")
    public ResponseEntity getTipoProductos(){
        List<TipoProductoResponse> saborResponseList = this.catalogService.getTipoProducto();
        return  new ResponseEntity(saborResponseList, HttpStatus.OK);
    }

    @GetMapping("/cliente")
    public ResponseEntity<List<ClienteResponse>> getClientes(@RequestParam(value = "search", required = false) Optional<String> search){
        List<ClienteResponse> clienteResponseList;
        if (search.isEmpty()){
            clienteResponseList = this.clienteService.getClientes();
        }else{
            clienteResponseList = this.clienteService.getClientes(search.get());
        }
        return  new ResponseEntity(clienteResponseList, HttpStatus.OK);
    }

}
