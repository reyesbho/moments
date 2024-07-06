package com.astra.moments.service;

import com.astra.moments.dto.TamanoProductoResponse;
import com.astra.moments.dto.TipoCobroResponse;
import com.astra.moments.exception.EntityNotFoundException;
import com.astra.moments.model.TamanoProducto;
import com.astra.moments.model.TipoCobro;
import com.astra.moments.repository.TamanoProductoRepository;
import com.astra.moments.util.MapObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Transactional
    public void deleteTamano(Long idSize){
        Optional<TamanoProducto> optionalTamanoProducto = this.tamanoProductoRepository.findById(idSize);
        if (optionalTamanoProducto.isEmpty()){
            throw new EntityNotFoundException("Error al buscar el tamaño");
        }
        try {
            this.tamanoProductoRepository.delete(optionalTamanoProducto.get());
        }catch (Exception e){
            throw  new RuntimeException("Error al eliminar el tamaño");
        }
    }


    @Transactional
    public TamanoProductoResponse updateStatus(Long idSize, Boolean status){
        Optional<TamanoProducto> optionalTamanoProducto = this.tamanoProductoRepository.findById(idSize);
        if (optionalTamanoProducto.isEmpty()){
            throw new EntityNotFoundException("Error al buscar el tamaño");
        }
        TamanoProducto tamanoProducto = optionalTamanoProducto.get();
        tamanoProducto.setEstatus(status);
        this.tamanoProductoRepository.save(tamanoProducto);
        return MapObject.mapToTamanoProductoResponse(tamanoProducto);
    }

}
