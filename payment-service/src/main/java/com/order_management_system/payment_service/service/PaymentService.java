package com.order_management_system.payment_service.service;

import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public String makePayment(double amount){
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
        return UUID.randomUUID().toString();
    }
}
