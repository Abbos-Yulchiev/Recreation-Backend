package com.epam.recreation_module.model.DTO;

import com.epam.recreation_module.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecreationDTO {

    private String name;
    private String description;
    private Integer availableSits;
    private List<String> openingTime;
    private List<String> closingTime;
    private String recreationCategory;
    private String recreationStatus;
    private double price;
    private List<Address> address;
}
