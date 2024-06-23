package com.astra.moments.service;

import com.astra.moments.dto.SaborResponse;
import com.astra.moments.model.Sabor;
import com.astra.moments.repository.SaborRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

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