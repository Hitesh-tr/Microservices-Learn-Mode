package com.order_management_system.order_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
    private long productId;
    private int quantity;

    public OrderRequest() {
    }

    public OrderRequest(long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
