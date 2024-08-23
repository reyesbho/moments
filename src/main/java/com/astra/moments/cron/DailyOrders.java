package com.astra.moments.cron;

import com.astra.moments.dto.PedidoResponse;
import com.astra.moments.service.NotificationSNSService;
import com.astra.moments.service.PedidoService;
import com.astra.moments.util.EstatusEnum;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DailyOrders {

    private PedidoService pedidoService;
    private NotificationSNSService notificationSNSService;

    public void notificationPedidos() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ROOT);
        String today = formatter.format(new Date());
        Page<PedidoResponse> pedidos = this.pedidoService.getPedidos(Optional.of("ALL"), today, today, PageRequest.of(0, 100));
        StringBuilder message = new StringBuilder("Pedidos para hoy "+today+"\n\t");
        pedidos.getContent().stream()
                        .filter(pedido -> !pedido.getEstatus().equalsIgnoreCase(EstatusEnum.CANCELED.getValue()))
                        .forEach(pedido -> message.append(pedido.getCliente().getNombre()+" - "+pedido.getLugarEntrega()+" - "+pedido.getEstatus() + " - Productos:"+pedido.getNumProductos()+"\n\t"));
        this.notificationSNSService.sendNotification(message.toString());
    }
}
