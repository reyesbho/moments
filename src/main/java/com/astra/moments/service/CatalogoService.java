package com.astra.moments.service;

import com.astra.moments.dto.SaborResponse;
import com.astra.moments.dto.TamanoProductoResponse;
import com.astra.moments.dto.TipoCobroResponse;
import com.astra.moments.dto.TipoProductoResponse;
import com.astra.moments.model.Sabor;
import com.astra.moments.model.TamanoProducto;
import com.astra.moments.model.TipoCobro;
import com.astra.moments.model.TipoProducto;
import com.astra.moments.repository.SaborRepository;
import com.astra.moments.repository.TamanoProductoRepository;
import com.astra.moments.repository.TipoCobroRepository;
import com.astra.moments.repository.TipoProductoRepository;
import com.astra.moments.util.MapObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogoService {

    private SaborRepository saborRepository;
    private TamanoProductoRepository tamanoProductoRepository;
    private TipoProductoRepository tipoProductoRepository;
    private TipoCobroRepository tipoCobroRepository;

    public CatalogoService(SaborRepository saborRepository, TamanoProductoRepository tamanoProductoRepository,
     TipoProductoRepository tipoProductoRepository,
     TipoCobroRepository tipoCobroRepository){
        this.saborRepository = saborRepository;
        this.tamanoProductoRepository = tamanoProductoRepository;
        this.tipoCobroRepository = tipoCobroRepository;
        this.tipoProductoRepository = tipoProductoRepository;
    }

    public List<SaborResponse> getSabores(){
        List<Sabor> saborList = this.saborRepository.findAll();
        return  saborList.stream().map(MapObject::mapToSaborResponse).toList();
    }

    public List<TamanoProductoResponse> getTamanos(){
        List<TamanoProducto> tamanos = this.tamanoProductoRepository.findAll();
        return  tamanos.stream().map(MapObject::mapToTamanoProductoResponse).toList();
    }

    public List<TipoCobroResponse> getTipoCobros(){
        List<TipoCobro> cobros = this.tipoCobroRepository.findAll();
        return cobros.stream().map(MapObject::mapToTipoCobroResponse).toList();
    }

    public List<TipoProductoResponse> getTipoProducto(){
        List<TipoProducto> tipoProductos = this.tipoProductoRepository.findAll();
        return tipoProductos.stream().map(MapObject::mapToTipoProductoResponse).toList();
    }

}
