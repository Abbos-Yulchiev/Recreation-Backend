package com.epam.recreation_module.model.enums;

public enum HMACSecretKeys {

    RECREATION("recreationKey"),
    CITIZEN_ACCOUNT("citizenAccountKey"),
    CITY_MANAGEMENT("cityManagementSecretKey");

    public final String secretKey;

    HMACSecretKeys(String secretKey) {
        this.secretKey = secretKey;
    }
}
