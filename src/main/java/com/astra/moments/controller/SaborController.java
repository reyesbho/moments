package com.astra.moments.controller;

import com.astra.moments.dto.SaborResponse;
import com.astra.moments.service.SaborService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/catalogo")
public class SaborController {

    private SaborService saborService;

    public SaborController(SaborService saborService){
        this.saborService = saborService;
    }

    @GetMapping("/sabor")
    public ResponseEntity getSabores(){
        List<SaborResponse> saborResponseList = this.saborService.getSabores();
        return  new ResponseEntity(saborResponseList, HttpStatus.OK);
    }

}
