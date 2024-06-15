package com.astra.moments.service;

import com.astra.moments.dto.ClienteResponse;
import com.astra.moments.model.Cliente;
import com.astra.moments.repository.ClienteRepository;
import com.astra.moments.util.MapObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {
    private ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    public List<ClienteResponse> getClientes(){
        List<Cliente> clienteList = this.clienteRepository.findAll();
        return  clienteList.stream().map(MapObject::mapToClienteResponse).collect(Collectors.toList());
    }

    public List<ClienteResponse> getClientes(String search){
        List<Cliente> clienteList = this.clienteRepository.findByNombreContainingIgnoreCaseOrApellidoPaternoContainingIgnoreCase(search, search);
        return  clienteList.stream().map(MapObject::mapToClienteResponse).collect(Collectors.toList());
    }

}
