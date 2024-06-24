package com.astra.moments.controller;

import com.astra.moments.dto.ClienteResponse;
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
@RequestMapping("/api/cliente")
public class ClienteController {

    private ClienteService clienteService;

    public ClienteController(ClienteService clienteService){
        this.clienteService = clienteService;
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
