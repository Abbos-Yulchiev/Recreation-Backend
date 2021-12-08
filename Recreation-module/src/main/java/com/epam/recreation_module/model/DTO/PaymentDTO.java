package com.epam.recreation_module.model.DTO;

import lombok.Data;

@Data
public class PaymentDTO {

    private Long orderId;

    private String redirectUrl;
}
