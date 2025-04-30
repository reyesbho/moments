package com.astra.moments.controller;

import com.astra.moments.config.JwtService;
import com.astra.moments.dto.ClienteResponse;
import com.astra.moments.dto.PedidoRequest;
import com.astra.moments.dto.PedidoResponse;
import com.astra.moments.model.User;
import com.astra.moments.service.PedidoService;
import com.astra.moments.util.EstatusEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

@WebMvcTest(value = PedidoController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PedidoService pedidoService;

    @MockBean
    private JwtService jwtService;

    PedidoResponse pedido1;
    PedidoResponse pedido2;
    PedidoResponse pedido3;
    PedidoResponse pedido4;

    @BeforeEach
    public void init(){
        pedido1 = PedidoResponse.builder().id(1l).fechaEntrega(new Date("30/06/2024 16:00:00"))
                .lugarEntrega("Tacahua").estatus(EstatusEnum.BACKLOG.toString()).total(200f).fechaRegistro(new Date())
                .fechaActualizacion(null)
                .registradoPor("Reyes")
                .cliente(ClienteResponse.builder().id(1l).build())
                .build();

        pedido2 = PedidoResponse.builder().id(2l).fechaEntrega(new Date("30/07/2024 16:00:00"))
                .lugarEntrega("Tacahua").estatus(EstatusEnum.DONE.toString()).total(500f).fechaRegistro(new Date())
                .fechaActualizacion(null)
                .registradoPor("Reyes")
                .cliente(ClienteResponse.builder().id(1l).build())
                .build();
        pedido3 = PedidoResponse.builder().id(3l).fechaEntrega(new Date("20/06/2024  08:00:00"))
                .lugarEntrega("Tacahua").estatus(EstatusEnum.CANCELED.toString()).total(1200f).fechaRegistro(new Date())
                .fechaActualizacion(null)
                .registradoPor("Reyes")
                .cliente(ClienteResponse.builder().id(1l).build())
                .build();
        pedido4 = PedidoResponse.builder().id(4l).fechaEntrega(new Date("25/06/2024 20:00:00"))
                .lugarEntrega("Tacahua").estatus(EstatusEnum.BACKLOG.toString()).total(100f).fechaRegistro(new Date())
                .fechaActualizacion(null)
                .registradoPor("Reyes")
                .cliente(ClienteResponse.builder().id(1l).build())
                .build();
    }


    @Test
    void getPedidos() {
    }

    @Test
    void getPedido() {
    }

    @Test
    void getProductosByPedido() {
    }

    @Test
    void updateEstatusPedido() {
    }

    @Test
    void addPedido() throws Exception {
        // for get authentication
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        PedidoRequest pedidoRequest = PedidoRequest.builder()
                .fechaEntrega(1745164837L)
                .lugarEntrega("Tacahua")
                .cliente(ClienteResponse.builder().id(1l).build())
                .build();


        Mockito.when(pedidoService.addPedido(Mockito.mock(PedidoRequest.class),Mockito.mock(User.class)))
                .thenReturn(pedido1);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/pedido")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedidoRequest)));

        result.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void addProductToPedido() {
    }

    @Test
    void deletePedido() throws Exception {
        Mockito.doNothing().when(pedidoService).deletePedido(Mockito.anyLong());

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/api/pedido/1")
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteProductoPedido() throws Exception {
        Mockito.doNothing().when(pedidoService).deleteProductoPedido(Mockito.anyLong());

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/api/pedido/1/producto/1")
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }
}