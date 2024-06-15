package com.astra.moments.util;


import com.astra.moments.dto.*;
import com.astra.moments.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class MapObject {
    private static Logger LOGGER = LoggerFactory.getLogger(MapObject.class);

    public static PedidoResponse mapToPedidoResponse(Pedido pedido){
        return PedidoResponse.builder().
                id(pedido.getId())
                .fechaEntrega(pedido.getFechaEntrega())
                .fechaRegistro(pedido.getFechaRegistro())
                .lugarEntrega(pedido.getLugarEntrega())
                .total(pedido.getTotal())
                .fechaActualizacion(pedido.getFechaActualizacion())
                .estatus(pedido.getEstatus())
                .cliente(mapToClienteResponse(pedido.getCliente()))
                .numProductos(pedido.getNumProductos())
                .registradoPor(pedido.getRegistradoPor())
                .horaEntrega(pedido.getHoraEntrega())
                .build();
    }

    public static ClienteResponse mapToClienteResponse(Cliente cliente) {
        return ClienteResponse.builder()
                .id(cliente.getId())
                .nombre(cliente.getNombre())
                .apellidoPaterno(cliente.getApellidoPaterno())
                .apellidoMaterno(cliente.getApellidoMaterno())
                .direccion(cliente.getDireccion()).build();

    }


    public static ProductoPedidoResponse mapToPedidoProductoResponse(ProductoPedido pedidoProducto){
        return ProductoPedidoResponse.builder()
                .id(pedidoProducto.getId())
                .idPedido(pedidoProducto.getIdPedido())
                .producto(mapToProductResponse(pedidoProducto.getProducto()))
                .sabor(mapToSaborResponse(pedidoProducto.getSabor()))
                .tipoProducto(mapToProductoTipoResponse(pedidoProducto.getTipoProducto()))
                .texto(pedidoProducto.getTexto())
                .comentarios(pedidoProducto.getComentarios())
                .fechaRegistro(pedidoProducto.getFechaRegistro())
                .fechaActualizacion(pedidoProducto.getFechaActualizacion())
                .porciones(pedidoProducto.getSize())
                .precio(pedidoProducto.getPrecio())
                .build();
    }


    public static ProductoTipoResponse mapToProductoTipoResponse(ProductoTipo productoTipo){
        return ProductoTipoResponse.builder()
                .id(productoTipo.getIdProducto())
                .clave(productoTipo.getClave())
                .descripcion(productoTipo.getDescripcion())
                .estatus(productoTipo.getEstatus())
                .build();
    }

    public static SaborResponse mapToSaborResponse(Sabor sabor){
        if (Objects.isNull(sabor))
            return new SaborResponse();
        return SaborResponse.builder()
                .id(sabor.getId())
                .clave(sabor.getClave())
                .descripcion(sabor.getDescripcion())
                .estatus(sabor.getEstatus())
                .build();
    }

    public static ProductoResponse mapToProductResponse(Producto producto){
        return ProductoResponse.builder()
                .id(producto.getId())
                .clave(producto.getClave())
                .estatus(producto.getEstatus())
                .descripcion(producto.getDescripcion())
                .imagen(producto.getImagen())
                .cobroUnidad(producto.isCobroUnidad())
                .build();
    }
}
