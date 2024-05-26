package com.astra.moments.service;

import com.astra.moments.dto.PedidoRequest;
import com.astra.moments.dto.PedidoResponse;
import com.astra.moments.dto.ProductoPedidoRequest;
import com.astra.moments.dto.ProductoPedidoResponse;
import com.astra.moments.exception.EntityNotFoundException;
import com.astra.moments.model.*;
import com.astra.moments.repository.*;
import com.astra.moments.util.EstatusEnum;
import com.astra.moments.util.MapObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PedidoService {
    private PedidoRepository pedidoRepository;
    private PedidoProductoRepository pedidoProductoRepository;
    private ClienteRepository clienteRepository;
    private SaborRepository saborRepository;
    private ProductoTipoRepository productoTipoRepository;
    private ProductoRepository productoRepository;

    public PedidoService(PedidoRepository pedidoRepository,PedidoProductoRepository pedidoProductoRepository,
                         ClienteRepository clienteRepository, SaborRepository saborRepository,
                         ProductoTipoRepository productoTipoRepository, ProductoRepository productoRepository){
        this.pedidoRepository = pedidoRepository;
        this.pedidoProductoRepository = pedidoProductoRepository;
        this.clienteRepository = clienteRepository;
        this.saborRepository = saborRepository;
        this.productoRepository = productoRepository;
        this.productoTipoRepository = productoTipoRepository;
    }

    public Page<PedidoResponse> getPedidos(Optional<String> estatus,String date, Pageable pageable) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date dateFilter = formatter.parse(date);
        System.out.println(dateFilter);
        Page<Pedido> pagePedidos = null;
        if(estatus.isPresent() && ! estatus.get().equalsIgnoreCase("ALL")){
            pagePedidos = this.pedidoRepository.findByEstatusAndFechaEntrega(estatus.get(),dateFilter, pageable);
        }else {
            pagePedidos = this.pedidoRepository.findByFechaEntrega(dateFilter, pageable);
        }

        return new PageImpl<>(pagePedidos.getContent().stream().map(MapObject::mapToPedidoResponse).toList(),
                pageable, pagePedidos.getTotalElements());
    }

    public PedidoResponse getPedido(Long idPedido){
            Pedido pedido = this.pedidoRepository.findById(idPedido).orElse(null);
            return MapObject.mapToPedidoResponse(pedido);
    }


    public List<ProductoPedidoResponse> getProductosByPedido(Long idPedido){
        List<ProductoPedido> productos = this.pedidoProductoRepository.findByIdPedido(idPedido);
        return productos.stream().map(MapObject::mapToPedidoProductoResponse).toList();
    }


    @Transactional
    public void updateStatePedido(Long idPedido, String status) throws EntityNotFoundException {
        //validar que exista el pedido
        Optional<Pedido> pedidoOptional = this.pedidoRepository.findById(idPedido);
        if (pedidoOptional.isEmpty()){
            throw new EntityNotFoundException("El pedido no existe");
        }

        Pedido pedidoEntity = pedidoOptional.get();
        pedidoEntity.setEstatus(EstatusEnum.getStatusEnum(status).getValue());
        pedidoEntity.setFechaActualizacion(new Date());

        this.pedidoRepository.save(pedidoEntity);
    }

    @Transactional
    public PedidoResponse addPedido(PedidoRequest newPedido, String userName) throws EntityNotFoundException {
        //validar el cliente
        Cliente cliente = null;
        if (Objects.isNull(newPedido.getCliente().getId()) || newPedido.getCliente().getId() == 0){
            cliente = Cliente.builder()
                    .nombre(newPedido.getCliente().getNombre())
                    .apellidoPaterno(newPedido.getCliente().getApellidoPaterno())
                    .apellidoMaterno(newPedido.getCliente().getApellidoMaterno())
                    .direccion(newPedido.getCliente().getDireccion())
                    .build();
            this.clienteRepository.saveAndFlush(cliente);
        }else{
            Optional<Cliente> clienteOptional = this.clienteRepository.findById(newPedido.getCliente().getId());
            cliente = clienteOptional.get();
        }

        Pedido pedidoEntity = Pedido.builder()
                .fechaEntrega(newPedido.getFechaEntrega())
                .lugarEntrega(newPedido.getLugarEntrega())
                .estatus(EstatusEnum.INCOMPLETE.getValue())
                .total(newPedido.getTotal())
                .fechaRegistro(new Date())
                .fechaActualizacion(null)
                .cliente(cliente)
                .registradoPor(userName)
                .numProductos(0)
                .build();
        this.pedidoRepository.saveAndFlush(pedidoEntity);

        return MapObject.mapToPedidoResponse(pedidoEntity);
    }


    @Transactional
    public void addProductoToPedido(Long idPedido, ProductoPedidoRequest productoDto){
        Optional<Pedido> optionalPedido = this.pedidoRepository.findById(idPedido);
        if (optionalPedido.isPresent()){
            Pedido pedidoEntity=optionalPedido.get();
            Producto productoEntity = this.productoRepository.findById(productoDto.getIdProducto()).orElse(new Producto(productoDto.getIdProducto()));
            Sabor saborEntity = null;
            if(Objects.nonNull(productoDto.getIdSabor())){
                saborEntity = this.saborRepository.findById(productoDto.getIdSabor()).orElse(null);
            }

            Float totalProducto = productoDto.getPrecio() * (productoEntity.isCobroUnidad() ? 1f : productoDto.getPorciones());
            Float total = pedidoEntity.getTotal() + totalProducto;
            pedidoEntity.setTotal(total);

            ProductoTipo productoTipoEntity = this.productoTipoRepository.findById(productoDto.getIdTipoProducto()).orElse(new ProductoTipo(productoDto.getIdTipoProducto()));
            ProductoPedido producto=ProductoPedido.builder()
                    .idPedido(pedidoEntity.getId())
                    .producto(productoEntity)
                    .sabor(saborEntity)
                    .tipoProducto(productoTipoEntity)
                    .texto(productoDto.getTexto())
                    .comentarios(productoDto.getComentarios())
                    .fechaRegistro(new Date())
                    .fechaActualizacion(null)
                    .size(productoDto.getPorciones())
                    .precio(productoDto.getPrecio())
                    .build();
            this.pedidoProductoRepository.save(producto);
            this.pedidoRepository.save(pedidoEntity);
        }
    }

}
