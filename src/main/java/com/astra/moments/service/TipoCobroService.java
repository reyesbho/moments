package com.astra.moments.service;

import com.astra.moments.dto.TipoCobroResponse;
import com.astra.moments.model.TipoCobro;
import com.astra.moments.repository.TipoCobroRepository;
import com.astra.moments.util.MapObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoCobroService {

    private TipoCobroRepository tipoCobroRepository;

    public TipoCobroService(TipoCobroRepository tipoCobroRepository){
        this.tipoCobroRepository = tipoCobroRepository;
    }


    public List<TipoCobroResponse> getTipoCobros(){
        List<TipoCobro> cobros = this.tipoCobroRepository.findAll();
        return cobros.stream().map(MapObject::mapToTipoCobroResponse).toList();
    }

}
