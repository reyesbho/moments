package com.astra.moments.service;

import com.astra.moments.dto.PedidoRequest;
import com.astra.moments.dto.PedidoResponse;
import com.astra.moments.dto.ProductoPedidoRequest;
import com.astra.moments.dto.ProductoPedidoResponse;
import com.astra.moments.exception.EntityNotFoundException;
import com.astra.moments.model.Cliente;
import com.astra.moments.model.Pedido;
import com.astra.moments.model.ProductoPedido;
import com.astra.moments.model.User;
import com.astra.moments.repository.*;
import com.astra.moments.util.EstatusEnum;
import com.astra.moments.util.MapObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PedidoService {
    private PedidoRepository pedidoRepository;
    private ProductoPedidoRepository productoPedidoRepository;
    private ClienteRepository clienteRepository;
    private SaborRepository saborRepository;
    private TipoProductoRepository tipoProductoRepository;
    private ProductoRepository productoRepository;

    public PedidoService(PedidoRepository pedidoRepository, ProductoPedidoRepository productoPedidoRepository,
                         ClienteRepository clienteRepository, SaborRepository saborRepository,
                         TipoProductoRepository tipoProductoRepository, ProductoRepository productoRepository){
        this.pedidoRepository = pedidoRepository;
        this.productoPedidoRepository = productoPedidoRepository;
        this.clienteRepository = clienteRepository;
        this.saborRepository = saborRepository;
        this.productoRepository = productoRepository;
        this.tipoProductoRepository = tipoProductoRepository;
    }

    public Page<PedidoResponse> getPedidos(Optional<String> estatus,String dateInit, String dateEnd, Pageable pageable) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date dateInitFilter = null;
        Date dateEndFilter = null;
        boolean hasFilterDate = true;
        boolean isSearchAll = estatus.isPresent() && estatus.get().equalsIgnoreCase("ALL");
        if(StringUtils.hasText(dateInit) && StringUtils.hasText(dateEnd)){
            dateInitFilter = formatter.parse(dateInit);
            dateEndFilter = formatter.parse(dateEnd);
        }else{
            hasFilterDate = false;
        }

        Page<Pedido> pagePedidos = null;
        if( isSearchAll && !hasFilterDate){
            pagePedidos = this.pedidoRepository.findAll(pageable);
        }
        if( isSearchAll && hasFilterDate){
            pagePedidos = this.pedidoRepository.findByFechaEntregaBetween(dateInitFilter, dateEndFilter, pageable);
        }
        if(!isSearchAll && hasFilterDate){
            pagePedidos = this.pedidoRepository.findByEstatusAndFechaEntregaBetween(estatus.get(),dateInitFilter, dateEndFilter, pageable);
        }
        if(!isSearchAll && !hasFilterDate){
            pagePedidos = this.pedidoRepository.findByEstatus(estatus.get(), pageable);
        }

        return new PageImpl<>(pagePedidos.getContent().stream().map(MapObject::mapToPedidoResponse).toList(),
                pageable, pagePedidos.getTotalElements());
    }

    public PedidoResponse getPedido(Long idPedido){
            Pedido pedido = this.pedidoRepository.findById(idPedido).orElse(null);
            return MapObject.mapToPedidoResponse(pedido);
    }


    public List<ProductoPedidoResponse> getProductosByPedido(Long idPedido){
        List<ProductoPedido> productos = this.productoPedidoRepository.findByIdPedido(idPedido);
        return productos.stream().map(MapObject::mapToPedidoProductoResponse).toList();
    }


    @Transactional
    public PedidoResponse updateStatePedido(Long idPedido, String status) throws EntityNotFoundException {
        //validar que exista el pedido
        Optional<Pedido> pedidoOptional = this.pedidoRepository.findById(idPedido);
        if (pedidoOptional.isEmpty()){
            throw new EntityNotFoundException("El pedido no existe");
        }

        Pedido pedidoEntity = pedidoOptional.get();
        pedidoEntity.setEstatus(EstatusEnum.getStatusEnum(status).getValue());
        pedidoEntity.setFechaActualizacion(new Date());

        Pedido pedido =  this.pedidoRepository.save(pedidoEntity);
        return MapObject.mapToPedidoResponse(pedido);
    }

    @Transactional
    public PedidoResponse addPedido(PedidoRequest newPedido, User currentUser) throws EntityNotFoundException {
        //validar el cliente
        Cliente cliente = null;
        if (Objects.isNull(newPedido.getCliente().getId()) || newPedido.getCliente().getId() == 0){
            cliente = Cliente.builder()
                    .nombre(newPedido.getCliente().getNombre())
                    .apellidoPaterno(newPedido.getCliente().getApellidoPaterno())
                    .apellidoMaterno(newPedido.getCliente().getApellidoMaterno())
                    .direccion(newPedido.getCliente().getDireccion())
                    .build();
            this.clienteRepository.save(cliente);
        }else{
            Optional<Cliente> clienteOptional = this.clienteRepository.findById(newPedido.getCliente().getId());
            cliente = clienteOptional.get();
        }

        Pedido pedidoEntity = Pedido.builder()
                .fechaEntrega(newPedido.getFechaEntrega())
                .horaEntrega(newPedido.getHoraEntrega())
                .lugarEntrega(newPedido.getLugarEntrega())
                .estatus(EstatusEnum.INCOMPLETE.getValue())
                .total(newPedido.getTotal())
                .fechaRegistro(new Date())
                .fechaActualizacion(null)
                .cliente(cliente)
                .registradoPor(currentUser.getUsername())
                .numProductos(0)
                .build();
        this.pedidoRepository.save(pedidoEntity);

        return MapObject.mapToPedidoResponse(pedidoEntity);
    }


    @Transactional
    public ProductoPedidoResponse addProductoToPedido(Long idPedido, ProductoPedidoRequest productoDto){
        Optional<Pedido> optionalPedido = this.pedidoRepository.findById(idPedido);
        /*if (optionalPedido.isPresent()){
            Pedido pedidoEntity=optionalPedido.get();
            Producto productoEntity = this.productoRepository.findById(productoDto.getIdDetalleProducto()).orElse(new Producto(productoDto.getIdDetalleProducto()));
            Sabor saborEntity = null;
            if(Objects.nonNull(productoDto.getIdSabor())){
                saborEntity = this.saborRepository.findById(productoDto.getIdSabor()).orElse(null);
            }

            Float totalProducto = productoDto.getPrecio() * (productoEntity.isCobroUnidad() ? 1f : productoDto.getPorciones());
            Float total = pedidoEntity.getTotal() + totalProducto;
            pedidoEntity.setTotal(total);

            Integer numProducts = pedidoEntity.getNumProductos() + 1 ;
            pedidoEntity.setNumProductos(numProducts);

            TipoProducto tipoProductoEntity = this.tipoProductoRepository.findById(productoDto.getIdTipoProducto()).orElse(new TipoProducto(productoDto.getIdTipoProducto()));
            ProductoPedido producto=ProductoPedido.builder()
                    .idPedido(pedidoEntity.getId())
                    .producto(productoEntity)
                    .sabor(saborEntity)
                    .tipoProducto(tipoProductoEntity)
                    .texto(productoDto.getTexto())
                    .comentarios(productoDto.getComentarios())
                    .fechaRegistro(new Date())
                    .fechaActualizacion(null)
                    .size(productoDto.getPorciones())
                    .precio(productoDto.getPrecio())
                    .build();
            ProductoPedido productoSaved = this.productoPedidoRepository.save(producto);
            this.pedidoRepository.save(pedidoEntity);

            return MapObject.mapToPedidoProductoResponse(productoSaved);
        }*/
        return  null;
    }

    @Transactional
    public void deleteProductoPedido(Long idProductoPedido){
        Optional<ProductoPedido> optionalProductoPedido = this.productoPedidoRepository.findById(idProductoPedido);
        if (optionalProductoPedido.isPresent()){
            ProductoPedido productoPedido = optionalProductoPedido.get();
            this.productoPedidoRepository.delete(productoPedido);
        }
    }

    @Transactional
    public void deletePedido(Long idPedido){
        //validate pedido
        Optional<Pedido> optionalPedido = this.pedidoRepository.findById(idPedido);
        if (optionalPedido.isEmpty()) {
            return;
        }
        Pedido pedido = optionalPedido.get();
        //seach all products by pedido
        List<ProductoPedido> productos = this.productoPedidoRepository.findByIdPedido(pedido.getId());
        //delete all productoPedido
        this.productoPedidoRepository.deleteAll(productos);
        //delete pedido
        this.pedidoRepository.delete(pedido);
    }
}
