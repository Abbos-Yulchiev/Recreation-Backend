package com.epam.recreation_module.model.DTO;

import com.epam.recreation_module.model.Street;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuildingDTO {

    private Street street;

    private Integer buildingCode;

    private Integer buildingNumber;
}
