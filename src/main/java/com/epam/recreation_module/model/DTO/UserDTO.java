package com.epam.recreation_module.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotBlank(message = "Citizen Id should not be empty")
    private String citizenId;

    @NotBlank(message = "Username should not be empty")
    private String username;

    @NotBlank(message = "Password should not be empty")
    private String password;

    @NotBlank(message = "Password should not be empty")
    private String prePassword;

    @NotBlank(message = "Role should not be empty")
    private String role;

}
