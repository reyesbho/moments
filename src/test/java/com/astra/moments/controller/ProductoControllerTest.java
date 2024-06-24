package com.astra.moments.controller;

import com.astra.moments.config.JwtService;
import com.astra.moments.dto.ProductoResponse;
import com.astra.moments.dto.TipoProductoResponse;
import com.astra.moments.service.ProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

@WebMvcTest( controllers = ProductoController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ProductoControllerTest {

    private static Logger LOGGER = LoggerFactory.getLogger(ProductoControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductoService productoService;

    @MockBean
    private  JwtService jwtService;

    private String token;
    private ProductoResponse producto;
    private ProductoResponse producto2;
    private ProductoResponse producto3;

    private TipoProductoResponse productoTipo;
    private TipoProductoResponse productoTipo2;
    private TipoProductoResponse productoTipo3;

    @BeforeEach
    void init(){

        producto = ProductoResponse.builder()
                .id(1l)
                .clave("pastel")
                .descripcion("Pastel")
                .estatus("ACTIVO")
                .imagen("https://algodulce.com.mx/cdn/shop/products/PINATEROCONFETTI_992x.jpg?v=1600198545")
                .build();

        producto2 = ProductoResponse.builder()
                .id(2l)
                .clave("pizza")
                .descripcion("Pizza")
                .estatus("ACTIVO")
                .imagen("https://algodulce.com.mx/cdn/shop/products/PINATEROCONFETTI_992x.jpg?v=1600198545")
                .build();
        producto3 = ProductoResponse.builder()
                .id(3l)
                .clave("gelatina")
                .descripcion("Gelatina")
                .estatus("INACTIVO")
                .imagen("https://algodulce.com.mx/cdn/shop/products/PINATEROCONFETTI_992x.jpg?v=1600198545")
                .build();


        productoTipo = TipoProductoResponse.builder()
                .id(1l)
                .clave("hawaiana")
                .descripcion("Hawayana")
                .estatus("ACTIVO").build();

        productoTipo2 = TipoProductoResponse.builder()
                .id(2l)
                .clave("peperoni")
                .descripcion("Peperoni")
                .estatus("ACTIVO").build();

        productoTipo3 = TipoProductoResponse.builder()
                .id(3l)
                .clave("mexicana")
                .descripcion("Mexicana")
                .estatus("ACTIVO").build();
    }


    @Test
    void getProductos() throws Exception {

        Mockito.when(productoService.getProductos())
                .thenReturn(Arrays.asList(producto, producto2));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/producto")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(2)));
    }

    @Test
    void getProducto() throws Exception {

        Mockito.when(productoService.getProducto(Mockito.anyLong()))
                .thenReturn(producto);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/producto/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.clave", CoreMatchers.is(producto.getClave())));
    }

}