package com.order_management_system.order_service.service;

import com.order_management_system.order_service.client.PaymentClient;
import com.order_management_system.order_service.dto.PaymentRequest;
import com.order_management_system.order_service.dto.PaymentResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {
    private final RestTemplate restTemplate;
    private final PaymentClient paymentClient;

    public OrderService(RestTemplate restTemplate, PaymentClient paymentClient) {
        this.restTemplate = restTemplate;
        this.paymentClient = paymentClient;
    }

    @CircuitBreaker(
        name = "paymentServiceCB",
        fallbackMethod = "paymentCircuitFallback"
    )
    @Retry(
        name = "paymentServiceRetry"
    )
    public String placeOrder(){
        PaymentRequest paymentRequest =
            new PaymentRequest(10L, 122);

        return callPayment(paymentRequest);
    }

    private String callPayment(PaymentRequest paymentRequest){
        PaymentResponse response =
            paymentClient.makePayment(paymentRequest);

        return "ORDER_PLACED_WITH_TXN_" + response.getTransactionId();
    }

    public String paymentCircuitFallback(Exception ex) {
        return "PAYMENT_SERVICE_UNAVAILABLE_FEIGN";
    }

    // Fallback must match method signature + Exception
    public String paymentFallback(Exception ex) {
        return "PAYMENT_SERVICE_UNAVAILABLE_CIRCUIT_OPEN";
    }

    public String paymentRetryFallback(Exception ex) {
        return "PAYMENT_FAILED_AFTER_RETRY";
    }
}
