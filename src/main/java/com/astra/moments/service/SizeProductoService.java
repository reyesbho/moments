package com.astra.moments.service;

import com.astra.moments.dto.SizeProductoRequest;
import com.astra.moments.dto.SizeProductoResponse;
import com.astra.moments.exception.EntityExistException;
import com.astra.moments.exception.EntityNotFoundException;
import com.astra.moments.model.SizeProducto;
import com.astra.moments.repository.SizeProductoRepository;
import com.astra.moments.util.MapObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SizeProductoService {

    private SizeProductoRepository sizeProductoRepository;

    public SizeProductoService(SizeProductoRepository sizeProductoRepository){
        this.sizeProductoRepository = sizeProductoRepository;
    }


    public List<SizeProductoResponse> getTamanos(){
        List<SizeProducto> tamanos = this.sizeProductoRepository.findAll();
        return  tamanos.stream().map(MapObject::mapToSizeProductoResponse).toList();
    }

    @Transactional
    public void deleteTamano(Long idSize){
        Optional<SizeProducto> optionalSizeProducto = this.sizeProductoRepository.findById(idSize);
        if (optionalSizeProducto.isEmpty()){
            throw new EntityNotFoundException("Error al buscar el tamaño");
        }
        try {
            this.sizeProductoRepository.delete(optionalSizeProducto.get());
        }catch (Exception e){
            throw  new RuntimeException("Error al eliminar el tamaño");
        }
    }


    @Transactional
    public SizeProductoResponse updateStatus(Long idSize, Boolean status){
        Optional<SizeProducto> optionalSizeProducto = this.sizeProductoRepository.findById(idSize);
        if (optionalSizeProducto.isEmpty()){
            throw new EntityNotFoundException("Error al buscar el tamaño");
        }
        SizeProducto sizeProducto = optionalSizeProducto.get();
        sizeProducto.setEstatus(status);
        this.sizeProductoRepository.save(sizeProducto);
        return MapObject.mapToSizeProductoResponse(sizeProducto);
    }

    @Transactional
    public SizeProductoResponse addSizeProduct(SizeProductoRequest sizeProductoRequest){
        Optional<SizeProducto> optionalSizeProducto = this.sizeProductoRepository.findByClave(sizeProductoRequest.getClave());
        if (optionalSizeProducto.isPresent()){
            throw new EntityExistException("El tamaño de prducto ya existe");
        }
        SizeProducto sizeProducto = SizeProducto.builder()
                .clave(sizeProductoRequest.getClave())
                .descripcion(sizeProductoRequest.getDescripcion())
                .estatus(Boolean.TRUE)
                .build();
        this.sizeProductoRepository.save(sizeProducto);
        return MapObject.mapToSizeProductoResponse(sizeProducto);
    }

}
