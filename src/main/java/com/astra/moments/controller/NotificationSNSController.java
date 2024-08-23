package com.astra.moments.controller;

import com.astra.moments.cron.DailyOrders;
import com.astra.moments.service.NotificationSNSService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/api/notification")
@AllArgsConstructor
public class NotificationSNSController {
    private NotificationSNSService notificationSNSService;
    private DailyOrders dailyOrders;

    @GetMapping("")
    public ResponseEntity<?> sendNotification(@RequestParam(value = "message", required = true) String message) throws ParseException {
        this.dailyOrders.notificationPedidos();
        return new ResponseEntity(HttpStatus.OK);
    }
}
