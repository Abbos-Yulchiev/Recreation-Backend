package com.epam.recreation_module.model.DTO;

import lombok.Data;

@Data
public class PaymentConfirmationDTO {

    private String message;

    private String citizenCardId;

    private String transactionId;

    private double amount;

    private boolean success;
}
