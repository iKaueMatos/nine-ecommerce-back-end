package com.api.apibackend.Order.Domain.service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.apibackend.Customer.Application.controller.ClientRequest;
import com.api.apibackend.Customer.Domain.service.CustomerOrderService;
import com.api.apibackend.Customer.Domain.service.CustomerSearchService;
import com.api.apibackend.Customer.Infra.persistence.entity.CustomerEntity;
import com.api.apibackend.Customer.Infra.persistence.repository.CustomerRepository;
import com.api.apibackend.CustomerAddress.Domain.model.CustomerAddressRequest;
import com.api.apibackend.CustomerAddress.Domain.service.CustomerAddressOrderService;
import com.api.apibackend.CustomerAddress.infra.entity.AddressEntity;
import com.api.apibackend.Order.Application.controller.OrderRequest;
import com.api.apibackend.Order.Domain.event.OrderCreatedEvent;
import com.api.apibackend.Order.Domain.repository.IOrderService;
import com.api.apibackend.Order.infra.entity.OrderEntity;
import com.api.apibackend.Order.infra.repository.OrderRepository;
import com.api.apibackend.OrderItem.Domain.services.OrderItemCreationService;
import com.api.apibackend.OrderItem.infra.entity.OrderItemEntity;
import com.api.apibackend.OrderItem.infra.repository.OrderItemRepository;
import com.api.apibackend.Product.Infra.entity.ProductEntity;
import com.api.apibackend.Product.Infra.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderCreationService implements IOrderService {

    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private CustomerRepository clientRepository;
    private OrderItemRepository orderItemRepository;
    private OrderItemCreationService orderItemCreationService;
    private CustomerSearchService customerSearchService;
    private CustomerOrderService customerOrderService;
    private CustomerAddressOrderService customerAddressOrderService;
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    public OrderCreationService(
        OrderRepository orderRepository,
        ProductRepository productRepository,
        CustomerRepository clientRepository,
        OrderItemRepository orderItemRepository,
        OrderItemCreationService orderItemCreationService,
        CustomerSearchService customerSearchService,
        CustomerOrderService customerOrderService,
        CustomerAddressOrderService customerAddressOrderService,
        ApplicationEventPublisher eventPublisher
    ) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderItemCreationService = orderItemCreationService;
        this.customerSearchService = customerSearchService;
        this.customerOrderService = customerOrderService;
        this.customerAddressOrderService = customerAddressOrderService;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public ResponseEntity<String> createOrder(OrderRequest orderRequest, CustomerAddressRequest customerAddress, ClientRequest clientRequest) {
        try {
            validateOrderRequest(orderRequest);

            OrderEntity newOrder = createOrderEntity(orderRequest, customerAddress, clientRequest);
            updateClientAndAddress(newOrder, clientRequest, customerAddress);
            List<OrderItemEntity> orderItems = orderItemCreationService.createOrderItems(orderRequest.getItems(), newOrder);

            saveOrderAndItems(newOrder, orderItems);
            finalizeOrder(orderItems);

            OrderCreatedEvent orderCreated = new OrderCreatedEvent(this, newOrder.getNumberOrder());
            eventPublisher.publishEvent(orderCreated);

            return ResponseEntity.status(HttpStatus.CREATED).body("Pedido Criado com sucesso");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor: " + ex.getMessage());
        }
    }

    private void validateOrderRequest(OrderRequest orderRequest) {
        if (orderRequest == null) {
            throw new IllegalArgumentException("Pedido não pode ser criado, objeto inválido");
        }
    }

    private OrderEntity createOrderEntity(OrderRequest orderRequest, CustomerAddressRequest customerAddress, ClientRequest clientRequest) {
        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setStatus("Pendente");
        orderEntity.setCustomerEmail(orderRequest.getCustomerEmail());
        orderEntity.setDatePayment(new Date());
        orderEntity.setPaymentMethod(orderRequest.getPaymentMethod());

        return orderEntity;
    }

    private void updateClientAndAddress(OrderEntity orderEntity, ClientRequest clientRequest, CustomerAddressRequest customerAddress) {
        Objects.requireNonNull(orderEntity, "A entidade de pedido não pode ser nulo");
        Objects.requireNonNull(clientRequest, "O pedido do cliente não pode ser nulo");
        Objects.requireNonNull(customerAddress, "O endereço do cliente não pode ser nulo");
    
        CustomerEntity existingClient = customerSearchService.findExistingClient(clientRequest);
    
        if (existingClient == null) {
            createAndSaveNewClient(orderEntity, clientRequest);
        } else {
            updateExistingClient(orderEntity, existingClient, customerAddress);
        }
    }
    
    private void createAndSaveNewClient(OrderEntity orderEntity, ClientRequest clientRequest) {
        CustomerEntity newClient = customerOrderService.createNewCustomerOrder(clientRequest);
        clientRepository.save(newClient);
        orderEntity.setClient(newClient);
    }
    
    private void updateExistingClient(OrderEntity orderEntity, CustomerEntity existingClient, CustomerAddressRequest customerAddress) {
        orderEntity.setClient(existingClient);
        AddressEntity existingAddress = existingClient.getAddress();
        AddressEntity newAddress = customerAddressOrderService.createAddressOrder(customerAddress);
    
        if (!existingAddress.isSameAddress(newAddress)) {
            existingClient.setAddress(newAddress);
            updateOrderEntityWithNewAddress(orderEntity, newAddress);
        }
    }
    
    private void updateOrderEntityWithNewAddress(OrderEntity orderEntity, AddressEntity newAddress) {
        orderEntity.setRoad(newAddress.getRoad());
        orderEntity.setNeighborhood(newAddress.getNeighborhood());
        orderEntity.setHousenumber(newAddress.getHousenumber());
        orderEntity.setCep(newAddress.getCep());
    }

    private void saveOrderAndItems(OrderEntity orderEntity, List<OrderItemEntity> orderItems) {
        orderEntity.setCustomerName(orderEntity.getClient().getName());
        orderEntity.setProducts(orderItems);
        orderEntity.calculateTotal();

        orderEntity = orderRepository.save(orderEntity);
        orderItemRepository.saveAll(orderItems);
    }

    private void finalizeOrder(List<OrderItemEntity> orderItems) {
        orderItems.forEach(this::updateProductStock);
    }

    private void updateProductStock(OrderItemEntity orderItem) {
        ProductEntity productEntity = orderItem.getProduct();
        int newStock = productEntity.getQuantityInStock() - orderItem.getQuantity();
        productEntity.setQuantityInStock(newStock);

        productRepository.save(productEntity);
    }
}
