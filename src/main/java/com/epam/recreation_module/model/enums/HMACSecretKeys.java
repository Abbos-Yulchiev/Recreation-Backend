package com.epam.recreation_module.model.enums;

public enum HMACSecretKeys{

    RECREATION("recreationKey");

    public final String secretKey;

    HMACSecretKeys(String secretKey) {
        this.secretKey = secretKey;
    }
}
