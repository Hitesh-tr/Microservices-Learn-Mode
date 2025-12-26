package com.order_management_system.order_service.service;

import com.order_management_system.order_service.dto.OrderRequest;
import com.order_management_system.order_service.dto.PaymentRequest;
import com.order_management_system.order_service.dto.PaymentResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {
    private final RestTemplate restTemplate;

    public OrderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String placeOrder(){
        PaymentRequest paymentRequest = new PaymentRequest(10L, 122);
        try{
            return callPayment(paymentRequest);
        } catch (ResourceAccessException ex){
            //retry once
            try{
                return callPayment(paymentRequest);
            }catch (ResourceAccessException retryEx) {
                return "PAYMENT_SERVICE_UNAVAILABLE_PLEASE_TRY_LATER";
            }
        }
    }

    private String callPayment(PaymentRequest paymentRequest){
        PaymentResponse response = restTemplate.postForObject(
            "http://localhost:8082/payments",
            paymentRequest,
            PaymentResponse.class
        );

        return "ORDER_PLACED_WITH_TXN_" + response.getTransactionId();
    }
}
