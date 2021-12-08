package com.epam.recreation_module.repository;

import com.epam.recreation_module.model.HMACSecretKey;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Done
 */
@DataJpaTest
class HMACSecretKeyRepositoryTest {

    @Autowired
    private HMACSecretKeyRepository hmacSecretKeyRepository;
    

    @Test
    void takeHMACSecretKeyByComponentName() {
        HMACSecretKey hmacSecretKey = new HMACSecretKey(
                1,
                "RECREATION",
                "recreationKey");
        hmacSecretKeyRepository.save(hmacSecretKey);
        String hotelSecretKey = hmacSecretKeyRepository.takeHMACSecretKeyByComponentName("RECREATION");
        assertEquals("recreationKey", hotelSecretKey);
    }
}