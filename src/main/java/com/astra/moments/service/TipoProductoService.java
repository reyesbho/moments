package com.astra.moments.service;

import com.astra.moments.dto.TipoProductoResponse;
import com.astra.moments.model.TipoProducto;
import com.astra.moments.repository.TipoProductoRepository;
import com.astra.moments.util.MapObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoProductoService {

    private TipoProductoRepository tipoProductoRepository;

    public TipoProductoService(TipoProductoRepository tipoProductoRepository){
        this.tipoProductoRepository = tipoProductoRepository;
    }

    public List<TipoProductoResponse> getTipoProducto(){
        List<TipoProducto> tipoProductos = this.tipoProductoRepository.findAll();
        return tipoProductos.stream().map(MapObject::mapToTipoProductoResponse).toList();
    }

}
