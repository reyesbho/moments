package com.astra.moments.service;

import com.astra.moments.dto.SaborRequest;
import com.astra.moments.dto.SaborResponse;
import com.astra.moments.exception.EntityExistException;
import com.astra.moments.exception.EntityNotFoundException;
import com.astra.moments.model.Sabor;
import com.astra.moments.repository.SaborRepository;
import com.astra.moments.util.MapObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SaborService {

    private SaborRepository saborRepository;

    public SaborService(SaborRepository saborRepository){
        this.saborRepository = saborRepository;
    }

    public List<SaborResponse> getSabores(){
        List<Sabor> saborList = this.saborRepository.findAll();
        return  saborList.stream().map(MapObject::mapToSaborResponse).toList();
    }

    @Transactional
    public void deleteSabor(Long idSabor){
        Optional<Sabor> optionalSabor = this.saborRepository.findById(idSabor);
        if (optionalSabor.isEmpty()){
            throw new EntityNotFoundException("Error al buscar el sabor");
        }
        try {
            this.saborRepository.delete(optionalSabor.get());
        }catch (Exception e){
            throw  new RuntimeException("Error al eliminar el sabor");
        }
    }

    @Transactional
    public SaborResponse updateStatus(Long idSabor, Boolean status){
        Optional<Sabor> optionalSabor = this.saborRepository.findById(idSabor);
        if (optionalSabor.isEmpty()){
            throw new EntityNotFoundException("Error al buscar el sabor");
        }
        Sabor sabor = optionalSabor.get();
        sabor.setEstatus(status);
        this.saborRepository.save(sabor);
        return MapObject.mapToSaborResponse(sabor);
    }

    @Transactional
    public SaborResponse addSabor(SaborRequest saborRequest){
        Optional<Sabor> optionalSabor = this.saborRepository.findByClave(saborRequest.getClave());
        if (optionalSabor.isPresent()){
            throw  new EntityExistException("El sabor ya existe");
        }
        Sabor sabor = Sabor.builder()
                .clave(saborRequest.getClave())
                .descripcion(saborRequest.getDescripcion())
                .estatus(Boolean.TRUE)
                .tags(saborRequest.getTags())
                .build();
        this.saborRepository.save(sabor);
        return MapObject.mapToSaborResponse(sabor);
    }
}
