package com.astra.moments.controller;

import com.astra.moments.dto.SaborResponse;
import com.astra.moments.dto.TamanoProductoResponse;
import com.astra.moments.service.TamanoProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{idSize}")
    public ResponseEntity deleteTamano(@PathVariable("idSize") Long idSize){
        this.tamanoProductoService.deleteTamano(idSize);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{idSize}/{estatus}")
    public ResponseEntity<TamanoProductoResponse> updateStatus(@PathVariable("idSize") Long idSize, @PathVariable("estatus") boolean estatus){
        TamanoProductoResponse tamanoProductoResponse = this.tamanoProductoService.updateStatus(idSize, estatus);
        return new ResponseEntity<>(tamanoProductoResponse, HttpStatus.OK);
    }

}
