package com.astra.moments.controller;

import com.astra.moments.dto.TipoCobroRequest;
import com.astra.moments.dto.TipoCobroResponse;
import com.astra.moments.service.TipoCobroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{idTipoCobro}")
    public ResponseEntity deleteTipoCobro(@PathVariable("idTipoCobro") Long idTipoCobro){
        this.tipoCobroService.deleteTipoCobro(idTipoCobro);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{idTipoCobro}/{estatus}")
    public ResponseEntity<TipoCobroResponse> updateStatus(@PathVariable("idTipoCobro") Long idTipoCobro, @PathVariable("estatus") boolean estatus){
        TipoCobroResponse tipoCobroResponse = this.tipoCobroService.updateStatus(idTipoCobro, estatus);
        return new ResponseEntity<>(tipoCobroResponse, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<TipoCobroResponse> createTipoCobro(@RequestBody TipoCobroRequest tipoCobroRequest){
        return new ResponseEntity<>(tipoCobroService.addTipoCobro(tipoCobroRequest), HttpStatus.CREATED);
    }

}
