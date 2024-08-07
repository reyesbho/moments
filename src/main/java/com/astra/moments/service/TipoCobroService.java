package com.astra.moments.service;

import com.astra.moments.dto.TipoCobroRequest;
import com.astra.moments.dto.TipoCobroResponse;
import com.astra.moments.exception.EntityExistException;
import com.astra.moments.exception.EntityNotFoundException;
import com.astra.moments.model.TipoCobro;
import com.astra.moments.repository.TipoCobroRepository;
import com.astra.moments.util.MapObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Transactional
    public void deleteTipoCobro(Long idTipoCobro){
        Optional<TipoCobro> optionalTipoCobro = this.tipoCobroRepository.findById(idTipoCobro);
        if (optionalTipoCobro.isEmpty()){
            throw new EntityNotFoundException("Error al buscar el tipo de cobro");
        }
        try {
            this.tipoCobroRepository.delete(optionalTipoCobro.get());
        }catch (Exception e){
            throw  new RuntimeException("Error al eliminar el tipo de cobro");
        }
    }

    @Transactional
    public TipoCobroResponse updateStatus(Long idTipoCobro, Boolean status){
        Optional<TipoCobro> optionalTipoCobro = this.tipoCobroRepository.findById(idTipoCobro);
        if (optionalTipoCobro.isEmpty()){
            throw new EntityNotFoundException("Error al buscar el tipo de cobro");
        }
        TipoCobro tipoCobro = optionalTipoCobro.get();
        tipoCobro.setEstatus(status);
        this.tipoCobroRepository.save(tipoCobro);
        return MapObject.mapToTipoCobroResponse(tipoCobro);
    }

    @Transactional
    public TipoCobroResponse addTipoCobro(TipoCobroRequest tipoCobroRequest){
        Optional<TipoCobro> optionalTipoCobro = this.tipoCobroRepository.findByClave(tipoCobroRequest.getClave());
        if (optionalTipoCobro.isPresent()){
            throw new EntityExistException("El tipo de cobo ya existe");
        }
        TipoCobro tipoCobro = TipoCobro.builder()
                .clave(tipoCobroRequest.getClave())
                .descripcion(tipoCobroRequest.getDescripcion())
                .estatus(Boolean.TRUE)
                .build();
        this.tipoCobroRepository.save(tipoCobro);
        return MapObject.mapToTipoCobroResponse(tipoCobro);
    }
}
