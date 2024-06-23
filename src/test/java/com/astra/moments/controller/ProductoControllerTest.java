package com.astra.moments.controller;

import com.astra.moments.config.JwtService;
import com.astra.moments.dto.ProductoResponse;
import com.astra.moments.dto.ProductoTipoResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    private ProductoTipoResponse productoTipo;
    private ProductoTipoResponse productoTipo2;
    private ProductoTipoResponse productoTipo3;

    @BeforeEach
    void init(){

        producto = ProductoResponse.builder()
                .id(1l)
                .clave("pastel")
                .descripcion("Pastel")
                .estatus("ACTIVO")
                .imagen("https://algodulce.com.mx/cdn/shop/products/PINATEROCONFETTI_992x.jpg?v=1600198545")
                .cobroUnidad(false)
                .build();

        producto2 = ProductoResponse.builder()
                .id(2l)
                .clave("pizza")
                .descripcion("Pizza")
                .estatus("ACTIVO")
                .imagen("https://algodulce.com.mx/cdn/shop/products/PINATEROCONFETTI_992x.jpg?v=1600198545")
                .cobroUnidad(false)
                .build();
        producto3 = ProductoResponse.builder()
                .id(3l)
                .clave("gelatina")
                .descripcion("Gelatina")
                .estatus("INACTIVO")
                .imagen("https://algodulce.com.mx/cdn/shop/products/PINATEROCONFETTI_992x.jpg?v=1600198545")
                .cobroUnidad(false)
                .build();


        productoTipo = ProductoTipoResponse.builder()
                .id(1l)
                .clave("hawaiana")
                .descripcion("Hawayana")
                .estatus("ACTIVO").build();

        productoTipo2 = ProductoTipoResponse.builder()
                .id(2l)
                .clave("peperoni")
                .descripcion("Peperoni")
                .estatus("ACTIVO").build();

        productoTipo3 = ProductoTipoResponse.builder()
                .id(3l)
                .clave("mexicana")
                .descripcion("Mexicana")
                .estatus("ACTIVO").build();
    }


    @Test
    void getProductos() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductoResponse> responseProductos = new PageImpl<>(Arrays.asList(producto, producto2), pageable, 2);

        Mockito.when(productoService.getProductos(Mockito.any(Optional.class),Mockito.any(Pageable.class)))
                .thenReturn(responseProductos);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/producto")
                        .queryParam("estatus","ALL")
                        .queryParam("page", "0")
                        .queryParam("size", "10")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", CoreMatchers.is(responseProductos.getContent().size())));
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

    @Test
    void getTipos() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        List<ProductoTipoResponse> responseProductos =Arrays.asList(productoTipo, productoTipo2, productoTipo3);

        Mockito.when(productoService.getProductTipo(Mockito.anyLong()))
                .thenReturn(responseProductos);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/producto/1/tipo")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(responseProductos.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].clave", CoreMatchers.is(responseProductos.get(0).getClave())));

    }
}