package com.astra.moments.service;

import com.astra.moments.dto.ClienteResponse;
import com.astra.moments.dto.PedidoRequest;
import com.astra.moments.dto.PedidoResponse;
import com.astra.moments.dto.ProductoPedidoResponse;
import com.astra.moments.model.*;
import com.astra.moments.repository.*;
import com.astra.moments.util.EstatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {
    @Mock
    private PedidoRepository pedidoRepository;
    @Mock
    private ProductoPedidoRepository productoPedidoRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private SaborRepository saborRepository;
    @Mock
    private TipoProductoRepository tipoProductoRepository;
    @Mock
    private ProductoRepository productoRepository;
    @InjectMocks
    private PedidoService pedidoService;

    private static Pedido pedido1;
    private static Pedido pedido2;
    private static Pedido pedido3;
    private static Pedido pedido4;

    @BeforeAll
    static void SetUp(){
        pedido1 = Pedido.builder().id(1l).fechaEntrega(new Date("30/06/2024 16:00:00"))
                .lugarEntrega("Tacahua").estatus(EstatusEnum.BACKLOG.toString()).total(200f).fechaRegistro(new Date())
                .fechaActualizacion(null)
                .registradoPor("Reyes")
                .cliente(Cliente.builder().id(1l).build())
                .build();

        pedido2 = Pedido.builder().id(2l).fechaEntrega(new Date("30/07/2024 16:00:00"))
                .lugarEntrega("Tacahua").estatus(EstatusEnum.DONE.toString()).total(500f).fechaRegistro(new Date())
                .fechaActualizacion(null)
                .registradoPor("Reyes")
                .cliente(Cliente.builder().id(1l).build())
                .build();
        pedido3 = Pedido.builder().id(3l).fechaEntrega(new Date("20/06/2024 08:00:00"))
                .lugarEntrega("Tacahua").estatus(EstatusEnum.CANCELED.toString()).total(1200f).fechaRegistro(new Date())
                .fechaActualizacion(null)
                .registradoPor("Reyes")
                .cliente(Cliente.builder().id(1l).build())
                .build();
        pedido4 = Pedido.builder().id(4l).fechaEntrega(new Date("25/06/2024 20:00:00"))
                .lugarEntrega("Tacahua").estatus(EstatusEnum.BACKLOG.toString()).total(100f).fechaRegistro(new Date())
                .fechaActualizacion(null)
                .registradoPor("Reyes")
                .cliente(Cliente.builder().id(1l).build())
                .build();
    }


    @Test
    @DisplayName("PedidoService_getAll_ReturnPagePedidoResponse")
    void getAllPedidos() throws ParseException {
                Mockito.when(pedidoRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(Mockito.mock(Page.class));

        Page<PedidoResponse> savedPedidos = pedidoService.getPedidos("ALL",null, null, Mockito.mock(PageRequest.class));

        Assertions.assertNotNull(savedPedidos);
    }

    @Test
    @DisplayName("PedidoService_getByStatus_ReturnPagePedidoResponse")
    void getAllPedidosByStatus() throws ParseException {
        Pageable pageable = PageRequest.of(0 ,10);

        Mockito.when(pedidoRepository.findByEstatus(Mockito.anyString(), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(pedido1, pedido4), pageable, 2));

        Page<PedidoResponse> savedPedidos = pedidoService.getPedidos("BACKLOG",null, null, pageable);
        Assertions.assertNotNull(savedPedidos);
        Assertions.assertEquals(savedPedidos.getContent().size(), 2);
    }

    @Test
    @DisplayName("PedidoService_getByDateRange_ReturnPagePedidoResponse")
    void getAllPedidosByDateRange() throws ParseException {
        Pageable pageable = PageRequest.of(0 ,10);

        Mockito.when(pedidoRepository.findByFechaEntregaBetween(Mockito.any(Date.class), Mockito.any(Date.class), Mockito.any(PageRequest.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(pedido3, pedido4, pedido1), pageable, 3));

        Page<PedidoResponse> savedPedidos = pedidoService.getPedidos("ALL","01-06-2011", "3-06-2011", pageable);
        Assertions.assertNotNull(savedPedidos);
        Assertions.assertEquals(savedPedidos.getContent().size(), 3);
    }
    @Test
    @DisplayName("PedidoService_getByStatusAndDateRange_ReturnPagePedidoResponse")
    void getAllPedidosByStatusAndDateRange() throws ParseException {
        Pageable pageable = PageRequest.of(0 ,10);

        Mockito.when(pedidoRepository.findByEstatusAndFechaEntregaBetween(Mockito.anyString(),Mockito.any(Date.class), Mockito.any(Date.class), Mockito.any(PageRequest.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(pedido1, pedido4), pageable, 2));

        Page<PedidoResponse> savedPedidos = pedidoService.getPedidos("BACKLOG","01-06-2011", "3-06-2011", pageable);
        Assertions.assertNotNull(savedPedidos);
        Assertions.assertEquals(savedPedidos.getContent().size(), 2);
    }

    @Test
    @DisplayName("PedidoService_getById_ReturnPedidoResponse")
    void getPedidoById() throws ParseException {

        Mockito.when(pedidoRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(pedido1));

        PedidoResponse pedido = pedidoService.getPedido(2l);
        Assertions.assertNotNull(pedido);
    }

    @Test
    @DisplayName("PedidoService_getProductosByPedidoId_ReturnListProductoPedidoResponse")
    void getProductosByPedido(){
        Mockito.when(productoPedidoRepository.findByPedidoId(Mockito.anyLong()))
                .thenReturn(Mockito.mock(List.class));

        List<ProductoPedidoResponse> productos = pedidoService.getProductosByPedido(2l);

        Assertions.assertNotNull(productos);
    }

    @Test
    @DisplayName("PedidoService_createPedido_ReturnListPedidoResponse")
    void addPedido() throws ParseException {
        Mockito.when(pedidoRepository.save(Mockito.any(Pedido.class)))
                .thenReturn(pedido1);
        Mockito.when(clienteRepository.save(Mockito.any(Cliente.class)))
                .thenReturn(Mockito.mock(Cliente.class));
        

        ClienteResponse clienteRequest = ClienteResponse.builder()
                .nombre("Reyes").apellidoPaterno("Bustamante").apellidoMaterno("Hernandez").direccion("Tacahua").build();
        PedidoRequest pedidoRequest = PedidoRequest.builder()
                .fechaEntrega(1745164837L)
                .lugarEntrega("Tacahua")
                .cliente(clienteRequest)
                .build();

        User user = User.builder()
                .id(1l).user("Reyes").email("reyes@gmail.com").fullName("Reyes Bustamante")
                .build();
                
        PedidoResponse pedido = pedidoService.addPedido(pedidoRequest, user);
        Assertions.assertNotNull(pedido);
        Assertions.assertEquals("INCOMPLETE", pedido.getEstatus());
        Assertions.assertNotNull(pedido.getFechaRegistro());
    }

    @Test
    @DisplayName("PedidoService_updateState_ReturnPedidoResponse")
    void updateStatePedido(){
        Mockito.when(pedidoRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(pedido1));
        Mockito.when(pedidoRepository.save(Mockito.any(Pedido.class)))
                .thenReturn(pedido1);

        PedidoResponse pedidoResponse = pedidoService.updateStatePedido(1l, "DONE");
        Assertions.assertNotNull(pedidoResponse);
        Assertions.assertEquals(pedidoResponse.getEstatus(), "DONE");
    }

    @Test
    @DisplayName("PedidoService_addProductoToPedido_ReturnProductoResponse")
    void addProductoToPedido(){
        /*Producto productoModel = Producto.builder()
                        .id(1l).clave("pizza").descripcion("Pizza").cobroUnidad(false)
                        .estatus("ACTIVO").imagen("some_url").build();
        TipoProducto tipoProductoModel = TipoProducto.builder().id(1l)
                        .clave("hawaiana").descripcion("Hawaiana").estatus("ACTIVO").idProducto(1l).build();
        ProductoPedido productoPedidoModel = ProductoPedido.builder()
                        .id(1l).idPedido(1l).producto(productoModel).sabor(null).tipoProducto(tipoProductoModel)
                        .texto(null).comentarios(null).fechaRegistro(new Date()).fechaActualizacion(null)
                        .size(12).precio(300).build();

        Mockito.when(pedidoRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(pedido1));
        Mockito.when(productoRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(productoModel));

        Mockito.when(tipoProductoRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(tipoProductoModel));
        Mockito.when(productoPedidoRepository.save(Mockito.any(ProductoPedido.class)))
                .thenReturn(productoPedidoModel);
        Mockito.when(pedidoRepository.save(Mockito.any(Pedido.class)))
                .thenReturn(pedido1);

        ProductoPedidoRequest productoPedidoRequest = ProductoPedidoRequest.builder()
                .idProducto(1l).idSabor(null).idTipoProducto(1l).texto(null)
                .comentarios(null).porciones(12).precio(300).build();

        ProductoPedidoResponse productoPedido = pedidoService.addProductoToPedido(1l, productoPedidoRequest);
        Assertions.assertNotNull(productoPedido);
        Assertions.assertNull(productoPedido.getSabor().getId());
        Assertions.assertEquals("hawaiana", productoPedido.getTipoProducto().getClave());*/
    }

    @Test
    @DisplayName("PedidoService_deleteProductoPedido_NoReturn")
    void deleteProductoPedido() {
        /*ProductoPedido productoPedido = ProductoPedido.builder()
                        .id(1l).idPedido(1l).detalleProducto(DetalleProducto.builder().id(1l).build())
                        .comentarios(null).fechaRegistro(new Date())
                        .fechaActualizacion(new Date()).build();

        Mockito.when(productoPedidoRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(productoPedido));

        Assertions.assertAll(() -> pedidoService.deleteProductoPedido(1l));*/

    }

    @Test
    @DisplayName("PedidoService_deletePedido_NoReturn")
    void deletePedido(){
        PedidoProducto productoPedido = PedidoProducto.builder()
                .id(1l).pedido(new Pedido(1l)).detalleProducto(DetalleProducto.builder().id(1l).build())
                .comentarios(null).fechaRegistro(new Date())
                .fechaActualizacion(new Date()).build();

        Mockito.when(productoPedidoRepository.findByPedidoId(Mockito.anyLong()))
                .thenReturn(Arrays.asList(productoPedido));
        Mockito.when(pedidoRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(pedido1));

        Assertions.assertAll(() -> pedidoService.deletePedido(1l));
    }
}
