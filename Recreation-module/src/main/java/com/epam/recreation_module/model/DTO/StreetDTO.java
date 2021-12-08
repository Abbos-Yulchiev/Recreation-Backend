package com.epam.recreation_module.model.DTO;

import com.epam.recreation_module.model.District;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StreetDTO {


    private String name;

    private District district;
}
