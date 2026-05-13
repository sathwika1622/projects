package com.example.eventmanagement.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public boolean processPayment(String cardNumber, String cvv) {
        // Dummy payment validation
        if (cardNumber == null || cardNumber.length() < 12) {
            return false;
        }
        if (cvv == null || cvv.length() < 3) {
            return false;
        }

        // Simulate a tiny delay for processing
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Always successful for dummy purposes if format is somewhat correct
        return true;
    }
}
