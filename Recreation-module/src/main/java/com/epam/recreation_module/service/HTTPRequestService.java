package com.epam.recreation_module.service;

import org.springframework.http.ResponseEntity;

public interface HTTPRequestService {

    ResponseEntity<?> makePOSTHTTPCallUsingHMAC(String keyId, String action, String path, String secretKey, String reqBody);

    ResponseEntity<?> makeGETHTTPCallUsingHMAC(String keyId, String action, String path, String secretKey);
}

