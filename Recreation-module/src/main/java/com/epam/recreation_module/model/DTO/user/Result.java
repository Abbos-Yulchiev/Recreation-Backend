package com.epam.recreation_module.model.DTO.user;

import com.epam.recreation_module.model.Address;
import lombok.Data;

@Data
public class Result {

    public int cardNumber;
    public String firstName;
    public String lastName;
    public String gender;
    public String birthDate;
    public Object parentId;
    public String maritalStatus;
    public String photoId;
    public Address address;
    public boolean active;
}
