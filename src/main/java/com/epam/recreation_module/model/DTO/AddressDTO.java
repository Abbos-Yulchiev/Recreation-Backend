package com.epam.recreation_module.model.DTO;

import com.epam.recreation_module.model.Building;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    private Integer id;

    private Integer homeCode;

    private String homeNumber;

    private Building building;
}
