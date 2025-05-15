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
                .estatusPago(pedido.getEstatusPago())
                .cliente(mapToClienteResponse(pedido.getCliente()))
                .registradoPor(pedido.getRegistradoPor())
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


    public static ProductoPedidoResponse mapToPedidoProductoResponse(PedidoProducto pedidoProducto){
        return ProductoPedidoResponse.builder()
                .id(pedidoProducto.getId())
                .idPedido(pedidoProducto.getPedido().getId())
                .sizeProducto(mapToSizeProductoResponse(pedidoProducto.getSizeProducto()))
                .producto(mapToProductResponse(pedidoProducto.getProducto()))
                .caracteristicas(pedidoProducto.getCaracteristicas())
                .cantidad(pedidoProducto.getCantidad())
                .fechaRegistro(pedidoProducto.getFechaRegistro())
                .fechaActualizacion(pedidoProducto.getFechaActualizacion())
                .precio(pedidoProducto.getPrecio())
                .build();
    }


    public static ProductoResponse mapToProductResponse(Producto producto){
        return ProductoResponse.builder()
                .id(producto.getId())
                .clave(producto.getClave())
                .estatus(producto.getEstatus())
                .descripcion(producto.getDescripcion())
                .imagen(producto.getImagen())
                .isCompleted(producto.isCompleted())
                .build();
    }


    public static SizeProductoResponse mapToSizeProductoResponse(SizeProducto sizeProducto){
        return SizeProductoResponse.builder()
                .id(sizeProducto.getId())
                .clave(sizeProducto.getClave())
                .descripcion(sizeProducto.getDescripcion())
                .estatus(sizeProducto.getEstatus())
                .tags(sizeProducto.getTags())
                .build();
    }


}
