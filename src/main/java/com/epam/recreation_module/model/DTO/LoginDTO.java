package com.epam.recreation_module.model.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginDTO {

    @NotNull(message = "username should not be empty!")
    private String username;

    @NotNull(message = "password should not be empty!")
    private String password;
}
