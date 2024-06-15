package com.astra.moments.service;

import com.astra.moments.dto.SaborResponse;
import com.astra.moments.model.Cliente;
import com.astra.moments.model.Sabor;
import com.astra.moments.repository.SaborRepository;
import jakarta.inject.Inject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SaborServiceTest {

    @Mock
    private SaborRepository saborRepository;

    @InjectMocks
    private SaborService saborService;

    @DisplayName("SaborService_getAll_ReturnListSaborResponse")
    @Test
    void getSabores() {
        List<Sabor> clientes = Mockito.mock(List.class);
        Mockito.when(saborRepository.findAll())
                .thenReturn(clientes);
        List<SaborResponse> sabores = this.saborService.getSabores();
        Assertions.assertNotNull(sabores);
    }
}