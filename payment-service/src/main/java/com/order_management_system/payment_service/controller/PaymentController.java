package com.order_management_system.payment_service.controller;

import com.order_management_system.payment_service.dto.PaymentRequest;
import com.order_management_system.payment_service.dto.PaymentResponse;
import com.order_management_system.payment_service.service.PaymentService;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public Optional<PaymentResponse> pay(@RequestBody PaymentRequest request){
        String txnId
            = paymentService.makePayment(request.getAmount());

        return Optional.of(new PaymentResponse("SUCCESS", txnId));
    }
}
