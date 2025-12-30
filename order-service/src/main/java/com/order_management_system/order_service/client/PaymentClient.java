package com.order_management_system.order_service.client;

import com.order_management_system.order_service.dto.PaymentRequest;
import com.order_management_system.order_service.dto.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    name = "payment-service"
)
public interface PaymentClient {

    @PostMapping("/payments")
    PaymentResponse makePayment(@RequestBody PaymentRequest request);
}
