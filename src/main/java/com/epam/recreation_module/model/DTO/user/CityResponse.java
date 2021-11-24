package com.epam.recreation_module.model.DTO.user;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class CityResponse{

    public String message;
    public Result result;
}
