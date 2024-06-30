package com.astra.moments.controller;

import com.astra.moments.dto.TipoCobroResponse;
import com.astra.moments.service.TipoCobroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tipoCobro")
public class TipoCobroController {

    private TipoCobroService tipoCobroService;

    public TipoCobroController(TipoCobroService tipoCobroService){
        this.tipoCobroService = tipoCobroService;
    }

    @GetMapping("")
    public ResponseEntity getTipoCobros(){
        List<TipoCobroResponse> saborResponseList = this.tipoCobroService.getTipoCobros();
        return  new ResponseEntity(saborResponseList, HttpStatus.OK);
    }

}
