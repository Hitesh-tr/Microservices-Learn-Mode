package com.order_management_system.payment_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {
    private Long orderId;
    private double amount;

    public PaymentRequest() {}

    public PaymentRequest(Long orderId, double amount) {
        this.orderId = orderId;
        this.amount = amount;
    }
}
