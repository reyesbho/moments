package com.astra.moments.service;

import com.astra.moments.dto.TipoProductoRequest;
import com.astra.moments.dto.TipoProductoResponse;
import com.astra.moments.exception.EntityExistException;
import com.astra.moments.exception.EntityNotFoundException;
import com.astra.moments.model.TipoProducto;
import com.astra.moments.repository.TipoProductoRepository;
import com.astra.moments.util.MapObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Transactional
    public void deleteTipoProducto(Long idTipoProducto){
        Optional<TipoProducto> optionalTipoProductoo = this.tipoProductoRepository.findById(idTipoProducto);
        if (optionalTipoProductoo.isEmpty()){
            throw new EntityNotFoundException("Error al buscar el tipo de cobro");
        }
        try {
            this.tipoProductoRepository.delete(optionalTipoProductoo.get());
        }catch (Exception e){
            throw  new RuntimeException("Error al eliminar el tipo de producto");
        }
    }

    @Transactional
    public TipoProductoResponse updateStatus(Long idTipoProducto, Boolean status){
        Optional<TipoProducto> optionalTipoProducto = this.tipoProductoRepository.findById(idTipoProducto);
        if (optionalTipoProducto.isEmpty()){
            throw new EntityNotFoundException("Error al buscar el tipo de producto");
        }
        TipoProducto tipoProducto = optionalTipoProducto.get();
        tipoProducto.setEstatus(status);
        this.tipoProductoRepository.save(tipoProducto);
        return MapObject.mapToTipoProductoResponse(tipoProducto);
    }

    @Transactional
    public TipoProductoResponse addTipoProducto(TipoProductoRequest tipoProductoRequest){
        Optional<TipoProducto> optionalTipoProducto = this.tipoProductoRepository.findByClave(tipoProductoRequest.getClave());
        if (optionalTipoProducto.isPresent()){
            throw  new EntityExistException("El tipo producto ya existe");
        }
        TipoProducto tipoProducto = TipoProducto.builder()
                .clave(tipoProductoRequest.getClave())
                .descripcion(tipoProductoRequest.getDescripcion())
                .estatus(Boolean.TRUE)
                .build();
        this.tipoProductoRepository.save(tipoProducto);
        return MapObject.mapToTipoProductoResponse(tipoProducto);
    }
}
