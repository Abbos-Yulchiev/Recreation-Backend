package com.epam.recreation_module.security.hmac;


import com.epam.recreation_module.repository.HMACSecretKeyRepository;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.Date;

@Component
public class HMACUtil implements HMACUtilService {

    private final HMACSecretKeyRepository hmacSecretKeyRepository;

    public HMACUtil(HMACSecretKeyRepository hmacSecretKeyRepository) {
        this.hmacSecretKeyRepository = hmacSecretKeyRepository;
    }

    public boolean hasAccess(String keyId, String timestamp, String action, String signature) {
        long now = new Date().getTime();
        if (now > Long.parseLong(timestamp)) {
            return false;
        }
//        String testSignature = calculateHASH(keyId, timestamp, action, hmacSecretKeyRepository.getSecretKeyByComponentName(keyId));
        String testSignature = calculateHASH(keyId, timestamp, action, hmacSecretKeyRepository.getSecretKeyByComponentName(keyId));
        return testSignature.equals(signature);
    }

    /**
     * To calculate HASH signature
     */
    public String calculateHASH(String keyId, String timestamp, String action, String secretKey) {

        String data = "keyId=" + keyId + ";timestamp=" + timestamp + ";action=" + action;
        try {
            SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data.getBytes());
            return new String(Base64.getEncoder().encode(rawHmac));
        } catch (GeneralSecurityException e) {
            throw new IllegalArgumentException();
        }
    }

    /*public HMACDTO getHmacHeader() {

        HMACDTO hmacdto = new HMACDTO();
        String keyId = "RECREATION";
        long time = new Date().getTime() + 300000;
        String action = "get_resident";
        String hmac = calculateHASH("RECREATION", String.valueOf(time), "get_resident", "recreationKey");
        hmacdto.setTimestamp(String.valueOf(time));
        hmacdto.setKeyId(keyId);
        hmacdto.setAction(action);
        hmacdto.setSecretKey(hmac);
        return hmacdto;
    }*/
    /*public static void main(String[] args) {

        long now = new Date().getTime() + 300000;
        System.out.println(now);
        String hmac = HMACUtil.calculateHASH("RECREATION", String.valueOf(now), "get_resident", "recreationKey");
        System.out.println(hmac);
    }*/
}
