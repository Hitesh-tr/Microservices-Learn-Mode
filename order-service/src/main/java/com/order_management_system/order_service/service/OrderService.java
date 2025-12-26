package com.order_management_system.order_service.service;

import com.order_management_system.order_service.dto.PaymentRequest;
import com.order_management_system.order_service.dto.PaymentResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {
    private final RestTemplate restTemplate;

    public OrderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(
        name = "paymentServiceCB",
        fallbackMethod = "paymentFallback"
    )
    public String placeOrder(){
        PaymentRequest paymentRequest = new PaymentRequest(10L, 122);

        return callPayment(paymentRequest);
    }

    private String callPayment(PaymentRequest paymentRequest){
        PaymentResponse response = restTemplate.postForObject(
            "http://localhost:8082/payments",
            paymentRequest,
            PaymentResponse.class
        );

        return "ORDER_PLACED_WITH_TXN_" + response.getTransactionId();
    }

    // Fallback must match method signature + Exception
    public String paymentFallback(Exception ex) {
        return "PAYMENT_SERVICE_UNAVAILABLE_CIRCUIT_OPEN";
    }
}
