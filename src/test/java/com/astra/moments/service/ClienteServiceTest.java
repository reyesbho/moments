package com.astra.moments.service;

import com.astra.moments.dto.ClienteResponse;
import com.astra.moments.model.Cliente;
import com.astra.moments.repository.ClienteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;
    private static Cliente cliente1;
    private static Cliente cliente2;
    @BeforeAll
    static void setUp(){
        cliente1= Cliente.builder().id(1l)
                .nombre("Reyes").apellidoPaterno("Bustamante")
                .apellidoMaterno("Hernandez").direccion("Tacahua").build();
        cliente2= Cliente.builder().id(2l)
                .nombre("Juan").apellidoPaterno("Bustamante")
                .apellidoMaterno("Sanchez").direccion("Tacahua").build();
    }

    @Test
    @DisplayName("ClienteService_getALl_ReturnListClienteResponse")
    void getClientes() {
        Mockito.when(clienteRepository.findAll())
                .thenReturn(Mockito.mock(List.class));
        List<ClienteResponse> clientes = clienteService.getClientes();

        Assertions.assertNotNull(clientes);
    }

    @Test
    @DisplayName("ClienteService_getAllContainsParamInNameOrApellido_ReturnListClienteResponse")
    void getClientesContainsInNameOrApellido() {
        Mockito.when(clienteRepository.findByNombreContainingIgnoreCaseOrApellidoPaternoContainingIgnoreCase(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Arrays.asList(cliente1, cliente2));
        List<ClienteResponse> clientes = clienteService.getClientes("Bustamante");

        Assertions.assertNotNull(clientes);
        Assertions.assertEquals(2, clientes.size());
    }

}