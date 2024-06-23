package com.astra.moments.service;

import com.astra.moments.dto.ProductoResponse;
import com.astra.moments.dto.ProductoTipoResponse;
import com.astra.moments.model.Producto;
import com.astra.moments.model.TipoProducto;
import com.astra.moments.repository.ProductoRepository;
import com.astra.moments.repository.TipoProductoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;
    @Mock
    private TipoProductoRepository tipoProductoRepository;
    @InjectMocks
    private ProductoService productoService;
    static Producto producto;
    static Producto producto2;
    static Producto producto3;

    static TipoProducto tipoProducto;
    static TipoProducto tipoProducto2;
    static TipoProducto tipoProducto3;

    @BeforeAll
    public static void setUp() {
        producto = Producto.builder()
                .id(1l)
                .clave("pastel")
                .descripcion("Pastel")
                .estatus("ACTIVO")
                .imagen("https://algodulce.com.mx/cdn/shop/products/PINATEROCONFETTI_992x.jpg?v=1600198545")
                .cobroUnidad(false)
                .build();

        producto2 = Producto.builder()
                .id(2l)
                .clave("pizza")
                .descripcion("Pizza")
                .estatus("ACTIVO")
                .imagen("https://algodulce.com.mx/cdn/shop/products/PINATEROCONFETTI_992x.jpg?v=1600198545")
                .cobroUnidad(false)
                .build();
        producto3 = Producto.builder()
                .id(3l)
                .clave("gelatina")
                .descripcion("Gelatina")
                .estatus("INACTIVO")
                .imagen("https://algodulce.com.mx/cdn/shop/products/PINATEROCONFETTI_992x.jpg?v=1600198545")
                .cobroUnidad(false)
                .build();

        tipoProducto = TipoProducto.builder()
                .id(1l)
                .clave("hawaiana")
                .descripcion("Hawayana")
                .estatus("ACTIVO")
                .idProducto(2l).build();

        tipoProducto2 = TipoProducto.builder()
                .id(2l)
                .clave("peperoni")
                .descripcion("Peperoni")
                .estatus("ACTIVO")
                .idProducto(2l).build();

        tipoProducto3 = TipoProducto.builder()
                .id(3l)
                .clave("mexicana")
                .descripcion("Mexicana")
                .estatus("ACTIVO")
                .idProducto(2l).build();
    }


    @DisplayName("ProductoService_getAllByStatus_ReturnPageProductoResponse")
    @Test
    void getProductos() {
        Pageable pageable = PageRequest.of(0, 10);

        Mockito.when(productoRepository.findByEstatus(Mockito.anyString(), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(producto, producto2), pageable, 2));

        Page<ProductoResponse> products = this.productoService.getProductos(Optional.of("ACTIVO"), pageable);
        Assertions.assertEquals(2, products.getContent().size());
    }

    @DisplayName("ProductoService_getAll_ReturnPageProductoResponse")
    @Test
    void getProductosByAll() {
        Page<Producto> productos = Mockito.mock(PageImpl.class);

        Mockito.when(productoRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(productos);

        Page<ProductoResponse> saveProducts = this.productoService.getProductos(Optional.empty(), Mockito.mock(PageRequest.class));
        Assertions.assertNotNull(saveProducts);
    }

    @Test
    @DisplayName("ProductoService_getById_ReturnProductoResponse")
    void getProducto(){
        Mockito.when(productoRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(producto2));

        ProductoResponse productoResponse = productoService.getProducto(2l);

        Assertions.assertNotNull(productoResponse);
        Assertions.assertEquals(2l, productoResponse.getId());
        Assertions.assertEquals("pizza", productoResponse.getClave());
        Assertions.assertEquals("Pizza", productoResponse.getDescripcion());
        Assertions.assertEquals("ACTIVO", productoResponse.getEstatus());
    }

    @Test
    @DisplayName("ProductoService_getTiposByProductoId_ReturnListProductoTipoResponse")
    void getProductoTipo(){
        Mockito.when(tipoProductoRepository.findByIdProducto(Mockito.anyLong()))
                .thenReturn(Arrays.asList(tipoProducto, tipoProducto2, tipoProducto3));

        List<ProductoTipoResponse> tipos = productoService.getProductTipo(2l);
        Assertions.assertEquals(3, tipos.size());
    }
}