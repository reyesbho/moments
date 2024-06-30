package com.astra.moments.controller;

import com.astra.moments.dto.TamanoProductoResponse;
import com.astra.moments.service.TamanoProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/size")
public class TamanoProductoController {

    private TamanoProductoService tamanoProductoService;

    public TamanoProductoController(TamanoProductoService tamanoProductoService){
        this.tamanoProductoService = tamanoProductoService;
    }

    @GetMapping("")
    public ResponseEntity getTamanos(){
        List<TamanoProductoResponse> saborResponseList = this.tamanoProductoService.getTamanos();
        return  new ResponseEntity(saborResponseList, HttpStatus.OK);
    }

}
