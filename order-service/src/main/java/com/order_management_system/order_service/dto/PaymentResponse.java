package com.order_management_system.order_service.dto;

import lombok.Getter;

@Getter
public class PaymentResponse {
    private String status;
    private String transactionId;

    public PaymentResponse(String status, String transactionId) {
        this.status = status;
        this.transactionId = transactionId;
    }
}