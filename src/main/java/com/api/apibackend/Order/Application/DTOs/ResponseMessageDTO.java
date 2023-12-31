package com.api.apibackend.Order.Application.DTOs;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.api.apibackend.Order.infra.persistence.entity.OrderEntity;
import com.api.apibackend.OrderAddress.Infra.persistence.entity.OrderAddressEntity;

import lombok.Data;

@Data
public class ResponseMessageDTO {
    private String message;
    private ResponseEntity<OrderEntity> order;
    private ResponseEntity<List<OrderEntity>> listOrders;
    private ResponseEntity<OrderAddressEntity> orderAddress;
    private String className;
    private String errorMessage;

    public ResponseMessageDTO(String message, ResponseEntity<OrderEntity> order, String className, String errorMessage) {
        this.message = message;
        this.order = order;
        this.className = className;
        this.errorMessage = errorMessage;
    }

    public ResponseMessageDTO(String message, ResponseEntity<OrderEntity> order, ResponseEntity<List<OrderEntity>> listOrders, ResponseEntity<OrderAddressEntity> orderAddress, String className, String errorMessage) {
        this.message = message;
        this.order = order;
        this.listOrders = listOrders;
        this.orderAddress = orderAddress;
        this.className = className;
        this.errorMessage = errorMessage;
    }

    public ResponseMessageDTO(String message, String className, String errorMessage) {
        this.message = message;
        this.className = className;
        this.errorMessage = errorMessage;
    }
    
      public ResponseMessageDTO(ResponseEntity<List<OrderEntity>> listOrders, String message, String className, String errorMessage) {
        this.listOrders = listOrders;
        this.message = message;
        this.className = className;
        this.errorMessage = errorMessage;
    }
}
