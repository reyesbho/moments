package com.astra.moments.service;

import com.astra.moments.dto.SaborResponse;
import com.astra.moments.model.Sabor;
import com.astra.moments.repository.SaborRepository;
import com.astra.moments.util.MapObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaborService {

    private SaborRepository saborRepository;

    public SaborService(SaborRepository saborRepository){
        this.saborRepository = saborRepository;
    }

    public List<SaborResponse> getSabores(){
        List<Sabor> saborList = this.saborRepository.findAll();
        return  saborList.stream().map(MapObject::mapToSaborResponse).collect(Collectors.toList());
    }


}
