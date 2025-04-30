package com.astra.moments.controller;

import com.astra.moments.dto.ClienteRequest;
import com.astra.moments.dto.ClienteResponse;
import com.astra.moments.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    private ClienteService clienteService;

    public ClienteController(ClienteService clienteService){
        this.clienteService = clienteService;
    }

    @GetMapping("")
    public ResponseEntity<List<ClienteResponse>> getClientes(@RequestParam(value = "search", required = false) Optional<String> search){
        List<ClienteResponse> clienteResponseList;
        if (search.isEmpty()){
            clienteResponseList = this.clienteService.getClientes();
        }else{
            clienteResponseList = this.clienteService.getClientes(search.get());
        }
        return  new ResponseEntity(clienteResponseList, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ClienteResponse> addCliente(@RequestBody @Validated ClienteRequest clienteRequest){
        ClienteResponse clienteResponse = this.clienteService.addCliente(clienteRequest);
        return new ResponseEntity<>(clienteResponse, HttpStatus.CREATED);
    }

}
