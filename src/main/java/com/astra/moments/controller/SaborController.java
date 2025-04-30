package com.astra.moments.controller;

import com.astra.moments.dto.SaborRequest;
import com.astra.moments.dto.SaborResponse;
import com.astra.moments.service.SaborService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sabor")
public class SaborController {

    private SaborService saborService;

    public SaborController(SaborService saborService){
        this.saborService = saborService;
    }

    @GetMapping("")
    public ResponseEntity getSabores(){
        List<SaborResponse> saborResponseList = this.saborService.getSabores();
        return  new ResponseEntity(saborResponseList, HttpStatus.OK);
    }

    @DeleteMapping("/{idSabor}")
    public ResponseEntity deleteSabor(@PathVariable("idSabor") Long idSabor){
        this.saborService.deleteSabor(idSabor);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{idSabor}/{estatus}")
    public ResponseEntity<SaborResponse> updateStatus(@PathVariable("idSabor") Long idSabor, @PathVariable("estatus") boolean estatus){
        SaborResponse saborResponse = this.saborService.updateStatus(idSabor, estatus);
        return new ResponseEntity<>(saborResponse, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<SaborResponse> addSabor(@RequestBody @Validated SaborRequest saborRequest){
        return new ResponseEntity<>(this.saborService.addSabor(saborRequest),HttpStatus.CREATED);
    }
}
