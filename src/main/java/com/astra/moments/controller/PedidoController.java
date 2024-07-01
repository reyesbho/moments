package com.astra.moments.controller;

import com.astra.moments.dto.PedidoRequest;
import com.astra.moments.dto.PedidoResponse;
import com.astra.moments.dto.ProductoPedidoRequest;
import com.astra.moments.dto.ProductoPedidoResponse;
import com.astra.moments.exception.EntityNotFoundException;
import com.astra.moments.model.User;
import com.astra.moments.service.PedidoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Optional;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {

    private static Logger LOGGER = LoggerFactory.getLogger(PedidoController.class);
    private PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService){
        this.pedidoService = pedidoService;
    }

    @GetMapping("")
    public ResponseEntity<Page<PedidoResponse>> getPedidos(@RequestParam(name = "estatus", required = false) Optional<String> estatus,
                                                           @RequestParam(name = "dateInit", required = false) String dateInit,
                                                           @RequestParam(name = "dateEnd", required = false) String dateEnd,
                                                           @RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                           @RequestParam(name = "size",  defaultValue = "10", required = false) int size) throws ParseException {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<PedidoResponse> pedidoResponseList = this.pedidoService.getPedidos(estatus, dateInit, dateEnd, pageRequest);
        return new ResponseEntity<>(pedidoResponseList, HttpStatus.OK);
    }

    @GetMapping("/{idPedido}")
    public ResponseEntity<PedidoResponse> getPedido(@PathVariable("idPedido") Long idPedido){
        PedidoResponse pedidoResponse = this.pedidoService.getPedido(idPedido);
        return new ResponseEntity(pedidoResponse, HttpStatus.OK);
    }

    @GetMapping("/{idPedido}/producto")
    public ResponseEntity<ProductoPedidoResponse> getProductosByPedido(@PathVariable("idPedido") Long idPedido){
        return new ResponseEntity(this.pedidoService.getProductosByPedido(idPedido), HttpStatus.OK);
    }

    @PutMapping("/{id}/{estatus}")
    @ResponseStatus(HttpStatus.OK)
    public void updateEstatusPedido(@PathVariable("id") Long id, @PathVariable("estatus") String estatus) throws EntityNotFoundException {
        this.pedidoService.updateStatePedido(id, estatus);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PedidoResponse> addPedido(@RequestBody PedidoRequest pedidoRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return  new ResponseEntity<>(this.pedidoService.addPedido(pedidoRequest, currentUser), HttpStatus.CREATED);
    }

    @PutMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PedidoResponse> updatePedido(@RequestBody PedidoRequest pedidoRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return  new ResponseEntity<>(this.pedidoService.updatePedido(pedidoRequest, currentUser), HttpStatus.CREATED);
    }

    @PostMapping("/{idPedido}/producto")
    public ResponseEntity addProductToPedido(@PathVariable("idPedido") Long id, @RequestBody ProductoPedidoRequest producto){
        ProductoPedidoResponse productoPedidoResponse = this.pedidoService.addProductoToPedido(id, producto);
        return new ResponseEntity(productoPedidoResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{idPedido}")
    public void deletePedido(@PathVariable("idPedido") Long id){
        this.pedidoService.deletePedido(id);
    }

    @DeleteMapping("/{idPedido}/producto/{idProductoPedido}")
    public void deleteProductoPedido(@PathVariable("idPedido") Long idPedido, @PathVariable("idProductoPedido") Long idProductoPedido){
        this.pedidoService.deleteProductoPedido(idProductoPedido);
    }
}
