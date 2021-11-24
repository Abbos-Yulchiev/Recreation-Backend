package com.epam.recreation_module.model.DTO;

import lombok.Data;

@Data
public class ConfirmEventDTO {

    private Long eventId;

    private boolean confirmed;
}
