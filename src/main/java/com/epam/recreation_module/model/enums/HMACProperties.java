package com.epam.recreation_module.model.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@NoArgsConstructor
@AllArgsConstructor
@Data
@PropertySource("classpath:url.properties")
public final class HMACProperties {

    final private String keyId = "RECREATION";
    final private String action = "get_resident";
    final private String secretKey = "recreationKey";

    @Value("${api.main.path}")
    private String mainPath;
}
