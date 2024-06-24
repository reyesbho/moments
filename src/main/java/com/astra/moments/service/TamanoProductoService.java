package com.astra.moments.service;

import com.astra.moments.dto.TamanoProductoResponse;
import com.astra.moments.model.TamanoProducto;
import com.astra.moments.repository.TamanoProductoRepository;
import com.astra.moments.util.MapObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TamanoProductoService {

    private TamanoProductoRepository tamanoProductoRepository;

    public TamanoProductoService( TamanoProductoRepository tamanoProductoRepository){
        this.tamanoProductoRepository = tamanoProductoRepository;
    }


    public List<TamanoProductoResponse> getTamanos(){
        List<TamanoProducto> tamanos = this.tamanoProductoRepository.findAll();
        return  tamanos.stream().map(MapObject::mapToTamanoProductoResponse).toList();
    }



}
