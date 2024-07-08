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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private DetalleProductoRepository detalleProductoRepository;
    private SaborRepository saborRepository;
    private TipoProductoRepository tipoProductoRepository;
    private Logger LOGGER = LoggerFactory.getLogger(PedidoService.class);

    public PedidoService(PedidoRepository pedidoRepository, ProductoPedidoRepository productoPedidoRepository,
                         ClienteRepository clienteRepository, DetalleProductoRepository detalleProductoRepository,
                         SaborRepository saborRepository, TipoProductoRepository tipoProductoRepository){
        this.pedidoRepository = pedidoRepository;
        this.productoPedidoRepository = productoPedidoRepository;
        this.clienteRepository = clienteRepository;
        this.detalleProductoRepository = detalleProductoRepository;
        this.saborRepository = saborRepository;
        this.tipoProductoRepository = tipoProductoRepository;
    }

    public Page<PedidoResponse> getPedidos(Optional<String> estatus,String dateInit, String dateEnd, Pageable pageable) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ROOT);
        Date dateInitFilter = null;
        Date dateEndFilter = null;
        boolean hasFilterDate = true;
        boolean isSearchAll = estatus.isPresent() && estatus.get().equalsIgnoreCase("ALL");
        if(StringUtils.hasText(dateInit) && StringUtils.hasText(dateEnd)){
            Date dateInitAux = formatter.parse(dateInit);
            Date dateEndAux = formatter.parse(dateEnd);

            Calendar cal = Calendar.getInstance();
            cal.setTime(dateInitAux);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);

            dateInitFilter = cal.getTime();
            cal.setTime(dateEndAux);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 999);
            dateEndFilter = cal.getTime();
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
                .lugarEntrega(newPedido.getLugarEntrega())
                .estatus(EstatusEnum.INCOMPLETE.getValue())
                .total(0f)
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
    public PedidoResponse updatePedido(PedidoRequest newPedido, User currentUser) throws EntityNotFoundException {
        Optional<Pedido> optionalPedido= this.pedidoRepository.findById(newPedido.getIdPedido());
        if (optionalPedido.isEmpty()){
            throw new EntityNotFoundException("Error al buscar el pedido");
        }
        Pedido pedido= optionalPedido.get();
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
        //pedido
        pedido.setFechaEntrega(newPedido.getFechaEntrega());
        pedido.setLugarEntrega(newPedido.getLugarEntrega());
        this.pedidoRepository.save(pedido);

        return MapObject.mapToPedidoResponse(pedido);
    }


    @Transactional
    public ProductoPedidoResponse addProductoToPedido(Long idPedido, ProductoPedidoRequest productoDto){
        Pedido pedidoEntity = this.pedidoRepository.findById(idPedido).
                orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado"));
        //validate detailProduct
        DetalleProducto detalleProducto =  this.detalleProductoRepository.findById(productoDto.getIdDetalleProducto())
                .orElseThrow(() -> new EntityNotFoundException("Detalle de producto no encontrado"));
        //validate sabor
        Sabor sabor =  this.saborRepository.findById(productoDto.getIdSabor()).orElse(null);
        //validate tipoProducto
        TipoProducto tipoProducto = this.tipoProductoRepository.findById(productoDto.getIdTipoProducto())
                .orElseThrow(() -> new EntityNotFoundException("Error al validar el tipo producto"));
        // total producto
        Float subTotalProduct = detalleProducto.getPrecio() * productoDto.getCantidad();
        Float totalProducto = subTotalProduct - productoDto.getDescuento();
        //total pedido
        Float totalPedido = pedidoEntity.getTotal() + totalProducto;
        pedidoEntity.setTotal(totalPedido);
        //total products
        Integer numProducts = pedidoEntity.getNumProductos() + 1 ;
        pedidoEntity.setNumProductos(numProducts);
        // entity productoPedido
        ProductoPedido productoPedido = ProductoPedido.builder()
                .idPedido(idPedido)
                .detalleProducto(detalleProducto)
                .comentarios(productoDto.getComentarios())
                .sabor(sabor)
                .tipoProducto(tipoProducto)
                .cantidad(productoDto.getCantidad())
                .fechaRegistro(new Date())
                .total(totalProducto)
                .descuento(productoDto.getDescuento())
                .build();

        this.productoPedidoRepository.save(productoPedido);
        return MapObject.mapToPedidoProductoResponse(productoPedido);
    }

    @Transactional
    public void deleteProductoPedido(Long idProductoPedido){
        Optional<ProductoPedido> optionalProductoPedido = this.productoPedidoRepository.findById(idProductoPedido);
        if (optionalProductoPedido.isEmpty()){
            throw  new EntityNotFoundException("Error al buscar el producto");
        }
        ProductoPedido productoPedido = optionalProductoPedido.get();
        //validate detailProduct
        DetalleProducto detalleProducto = productoPedido.getDetalleProducto();
        //pedido
        Optional<Pedido> optionalPedido = this.pedidoRepository.findById(productoPedido.getIdPedido());
        if (optionalPedido.isEmpty()){
            throw new EntityNotFoundException("Pedido no encontrado");
        }
        Pedido pedidoEntity = optionalPedido.get();
        // total
        Float totalProducto = detalleProducto.getPrecio() * productoPedido.getCantidad();
        Float total = pedidoEntity.getTotal() - totalProducto;
        pedidoEntity.setTotal(total);
        //total products
        Integer numProducts = pedidoEntity.getNumProductos() - 1;
        pedidoEntity.setNumProductos(numProducts);
        this.pedidoRepository.save(pedidoEntity);
        //delete
        this.productoPedidoRepository.delete(productoPedido);
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
