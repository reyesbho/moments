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
                .detalleProducto(mapToDetalleProductoResponse(pedidoProducto.getDetalleProducto()))
                .comentarios(pedidoProducto.getComentarios())
                .cantidad(pedidoProducto.getCantidad())
                .fechaRegistro(pedidoProducto.getFechaRegistro())
                .fechaActualizacion(pedidoProducto.getFechaActualizacion())
                .total(pedidoProducto.getSubTotal())
                .descuento(pedidoProducto.getDescuento())
                .build();
    }


    public static TipoProductoResponse mapToTipoProductoResponse(TipoProducto tipoProducto){
        if (Objects.isNull(tipoProducto))
            return null;
        return TipoProductoResponse.builder()
                .id(tipoProducto.getId())
                .clave(tipoProducto.getClave())
                .descripcion(tipoProducto.getDescripcion())
                .estatus(tipoProducto.getEstatus())
                .tags(tipoProducto.getTags())
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
                .tags(sabor.getTags())
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
                .build();
    }


    public static DetalleProductoResponse mapToDetalleProductoResponse(DetalleProducto detalleProducto){
        return DetalleProductoResponse.builder()
                .id(detalleProducto.getId())
                .producto(mapToProductResponse(detalleProducto.getProducto()))
                .size(mapToSizeProductoResponse(detalleProducto.getSize()))
                .sabor(mapToSaborResponse(detalleProducto.getSabor()))
                .tipoProducto(mapToTipoProductoResponse(detalleProducto.getTipoProducto()))
                .descripcion(detalleProducto.getDescripcion())
                .estatus(detalleProducto.getEstatus())
                .precio(detalleProducto.getPrecio())
                .fechaRegistro(detalleProducto.getFechaRegistro())
                .fechaActualizacion(detalleProducto.getFechaActualizacion())
                .imagen(detalleProducto.getImagen())
                .comentarios(detalleProducto.getComentarios())
                .build();
    }

}
