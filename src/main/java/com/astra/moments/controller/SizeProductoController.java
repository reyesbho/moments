package com.astra.moments.controller;

import com.astra.moments.dto.SizeProductoRequest;
import com.astra.moments.dto.SizeProductoResponse;
import com.astra.moments.service.SizeProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/size")
public class SizeProductoController {

    private SizeProductoService sizeProductoService;

    public SizeProductoController(SizeProductoService sizeProductoService){
        this.sizeProductoService = sizeProductoService;
    }

    @GetMapping("")
    public ResponseEntity getTamanos(){
        List<SizeProductoResponse> saborResponseList = this.sizeProductoService.getTamanos();
        return  new ResponseEntity(saborResponseList, HttpStatus.OK);
    }

    @DeleteMapping("/{idSize}")
    public ResponseEntity deleteTamano(@PathVariable("idSize") Long idSize){
        this.sizeProductoService.deleteTamano(idSize);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{idSize}/{estatus}")
    public ResponseEntity<SizeProductoResponse> updateStatus(@PathVariable("idSize") Long idSize, @PathVariable("estatus") boolean estatus){
        SizeProductoResponse sizeProductoResponse = this.sizeProductoService.updateStatus(idSize, estatus);
        return new ResponseEntity<>(sizeProductoResponse, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<SizeProductoResponse> addSizeProduct(@RequestBody SizeProductoRequest sizeProductoRequest){
        return  new ResponseEntity<>(this.sizeProductoService.addSizeProduct(sizeProductoRequest), HttpStatus.CREATED);
    }
}
