package com.astra.moments.service;

import com.astra.moments.dto.PedidoRequest;
import com.astra.moments.dto.PedidoResponse;
import com.astra.moments.dto.ProductoPedidoRequest;
import com.astra.moments.dto.ProductoPedidoResponse;
import com.astra.moments.exception.EntityNotFoundException;
import com.astra.moments.model.*;
import com.astra.moments.repository.*;
import com.astra.moments.util.EstatusEnum;
import com.astra.moments.util.EstatusPagoEnum;
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
    private ProductoRepository productoRepository;
    private SizeProductoRepository sizeProductoRepository;

    private Logger LOGGER = LoggerFactory.getLogger(PedidoService.class);

    public PedidoService(PedidoRepository pedidoRepository, ProductoPedidoRepository productoPedidoRepository,
                         ClienteRepository clienteRepository, ProductoRepository productoRepository, SizeProductoRepository sizeProductoRepository){
        this.pedidoRepository = pedidoRepository;
        this.productoPedidoRepository = productoPedidoRepository;
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
        this.sizeProductoRepository = sizeProductoRepository;
    }

    public Page<PedidoResponse> getPedidos(String estatus,String dateInit, String dateEnd, Pageable pageable) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ROOT);
        Date dateInitFilter = null;
        Date dateEndFilter = null;
        boolean hasFilterDate = true;
        boolean isSearchAll = estatus.equalsIgnoreCase("ALL");
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
            pagePedidos = this.pedidoRepository.findByEstatusAndFechaEntregaBetween(estatus,dateInitFilter, dateEndFilter, pageable);
        }
        if(!isSearchAll && !hasFilterDate){
            pagePedidos = this.pedidoRepository.findByEstatus(estatus, pageable);
        }

        return new PageImpl<>(pagePedidos.getContent().stream().map(MapObject::mapToPedidoResponse).toList(),
                pageable, pagePedidos.getTotalElements());
    }

    public PedidoResponse getPedido(Long idPedido){
            Pedido pedido = this.pedidoRepository.findById(idPedido).orElse(null);
            return MapObject.mapToPedidoResponse(pedido);
    }


    public List<ProductoPedidoResponse> getProductosByPedido(Long idPedido){
        List<PedidoProducto> productos = this.productoPedidoRepository.findByPedidoId(idPedido);
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
            cliente = this.clienteRepository.findById(newPedido.getCliente().getId()).orElse(null);
        }

        Pedido pedidoEntity = Pedido.builder()
                .fechaEntrega(new Date(newPedido.getFechaEntrega()))
                .lugarEntrega(newPedido.getLugarEntrega())
                .estatus(EstatusEnum.INCOMPLETE.getValue())
                .estatusPago(EstatusPagoEnum.PENDIENTE.getValue())
                .total(0f)
                .fechaRegistro(new Date())
                .fechaActualizacion(null)
                .cliente(cliente)
                .registradoPor(currentUser.getUsername())
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
        pedido.setFechaEntrega(new Date(newPedido.getFechaEntrega()));
        pedido.setLugarEntrega(newPedido.getLugarEntrega());
        this.pedidoRepository.save(pedido);

        return MapObject.mapToPedidoResponse(pedido);
    }


    @Transactional
    public ProductoPedidoResponse addProductoToPedido(Long idPedido, ProductoPedidoRequest productoDto){
        Pedido pedidoEntity = this.pedidoRepository.findById(idPedido).
                orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado"));
        //validate product
        Producto producto = this.productoRepository.findById(productoDto.getIdProducto())
                .orElseThrow(() -> new EntityNotFoundException("Prroducto no encontrado"));

        //validate size
        SizeProducto sizeProducto = this.sizeProductoRepository.findById(productoDto.getIdSize())
                .orElseThrow(() -> new EntityNotFoundException("Tamaño no encontrado"));

        // total producto
        Float subTotalProduct = productoDto.getPrecio() * productoDto.getCantidad();
        //total pedido
        Float totalPedido = pedidoEntity.getTotal() + subTotalProduct;
        pedidoEntity.setTotal(totalPedido);
        // entity productoPedido
        PedidoProducto productoPedido = PedidoProducto.builder()
                .pedido(pedidoEntity)
                .producto(producto)
                .sizeProducto(sizeProducto)
                .caracteristicas(productoDto.getCaracteristicas())
                .cantidad(productoDto.getCantidad())
                .fechaRegistro(new Date())
                .precio(productoDto.getPrecio())
                .build();

        this.productoPedidoRepository.save(productoPedido);
        return MapObject.mapToPedidoProductoResponse(productoPedido);
    }

    @Transactional
    public void deleteProductoPedido(Long idProductoPedido){
        Optional<PedidoProducto> optionalProductoPedido = this.productoPedidoRepository.findById(idProductoPedido);
        if (optionalProductoPedido.isEmpty()){
            throw  new EntityNotFoundException("Error al buscar el producto");
        }
        PedidoProducto productoPedido = optionalProductoPedido.get();
        //pedido
        Optional<Pedido> optionalPedido = this.pedidoRepository.findById(productoPedido.getPedido().getId());
        if (optionalPedido.isEmpty()){
            throw new EntityNotFoundException("Pedido no encontrado");
        }
        Pedido pedidoEntity = optionalPedido.get();
        // total
        Float totalProducto = productoPedido.getPrecio() * productoPedido.getCantidad();
        Float total = pedidoEntity.getTotal() - totalProducto;
        pedidoEntity.setTotal(total);
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
        List<PedidoProducto> productos = this.productoPedidoRepository.findByPedidoId(pedido.getId());
        //delete all productoPedido
        this.productoPedidoRepository.deleteAll(productos);
        //delete pedido
        this.pedidoRepository.delete(pedido);
    }
}
