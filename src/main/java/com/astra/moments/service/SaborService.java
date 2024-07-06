package com.astra.moments.service;

import com.astra.moments.dto.SaborResponse;
import com.astra.moments.dto.TamanoProductoResponse;
import com.astra.moments.exception.EntityNotFoundException;
import com.astra.moments.model.Sabor;
import com.astra.moments.model.TamanoProducto;
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
}
