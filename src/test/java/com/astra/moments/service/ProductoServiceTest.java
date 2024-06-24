package com.astra.moments.service;

import com.astra.moments.dto.ProductoResponse;
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
                .build();

        producto2 = Producto.builder()
                .id(2l)
                .clave("pizza")
                .descripcion("Pizza")
                .estatus("ACTIVO")
                .imagen("https://algodulce.com.mx/cdn/shop/products/PINATEROCONFETTI_992x.jpg?v=1600198545")
                .build();
        producto3 = Producto.builder()
                .id(3l)
                .clave("gelatina")
                .descripcion("Gelatina")
                .estatus("INACTIVO")
                .imagen("https://algodulce.com.mx/cdn/shop/products/PINATEROCONFETTI_992x.jpg?v=1600198545")
                .build();

        tipoProducto = TipoProducto.builder()
                .id(1l)
                .clave("hawaiana")
                .descripcion("Hawayana")
                .estatus("ACTIVO").build();

        tipoProducto2 = TipoProducto.builder()
                .id(2l)
                .clave("peperoni")
                .descripcion("Peperoni")
                .estatus("ACTIVO").build();

        tipoProducto3 = TipoProducto.builder()
                .id(3l)
                .clave("mexicana")
                .descripcion("Mexicana")
                .estatus("ACTIVO").build();
    }



    @DisplayName("ProductoService_getAll_ReturnListProductoResponse")
    @Test
    void getProductosByAll() {

        Mockito.when(productoRepository.findAll())
                .thenReturn(Mockito.mock(List.class));

        List<ProductoResponse> saveProducts = this.productoService.getProductos();
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
}