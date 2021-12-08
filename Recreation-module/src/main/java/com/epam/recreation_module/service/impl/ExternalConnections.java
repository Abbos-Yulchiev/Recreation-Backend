package com.epam.recreation_module.service.impl;

import com.epam.recreation_module.exception.NotFoundException;
import com.epam.recreation_module.exception.RestException;
import com.epam.recreation_module.payload.ApiResponse;
import com.epam.recreation_module.service.HTTPRequestService;
import com.epam.recreation_module.service.RecreationService;
import com.epam.recreation_module.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Configuration
@PropertySource("classpath:url.properties")
public class ExternalConnections {

    private final HTTPRequestService httpRequestService;
    private final UserService userService;
    private final RecreationService recreationService;

    @Value("${api.main.path}")
    private String mainPath;
    @Value("${api.citizen}")
    private String citizenPath;
    @Value("${api.create.newEvent.url}")
    private String eventUrl;
    @Value("${api.create.new.legalEntity}")
    private String newLegalEntity;
    @Value("${api.payment.path}")
    private String paymentPath;

    public ExternalConnections(HTTPRequestService httpRequestService,
                               UserService userService, @Lazy RecreationService recreationService) {
        this.httpRequestService = httpRequestService;
        this.userService = userService;
        this.recreationService = recreationService;
    }

    @Bean
    public RestTemplate
    restTemplate() {
        return new RestTemplate();
    }

    /**
     * @param id
     * @param name
     * @param description
     * @param startTime
     * @param endTime
     * @return
     */
    public ResponseEntity<?> creatingNewEvent(String id, String name, String
            description, String startTime, String endTime) {
        String reqBody = "{" +
                "\"id\":\"" + id + "\"," +
                "\"name\":\"" + name + "\"," +
                "\"description\":\"" + description + "\"," +
                "\"startDate\":\"" + startTime + "\"," +
                "\"endDate\":\"" + endTime + "\"" +
                "}";

        try {
            httpRequestService.makePOSTHTTPCallUsingHMAC(
                    "RECREATION",
                    "get_resident",
                    mainPath + eventUrl,
                    "recreationKey",
                    reqBody
            );
            return ResponseEntity.status(HttpStatus.CREATED).body("Request Accepted");
        } catch (RestException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Server not available!");
        }
    }

    /**
     * @param name
     * @param homeId
     * @param ownerCardNumber
     * @return responseEntity
     * Make post request to City Management module and send new Recreation place info
     */
    public ResponseEntity<?> creatingNewRecreationPlace(String name, Long homeId, String ownerCardNumber) {

        String reqBody = "{" +
                "\"name\":\"" + name + "\"," +
                "\"homeId\":" + homeId + "," +
                "\"ownerCardNumber\":" + ownerCardNumber +
                "}";

        try {
            ResponseEntity<?> responseEntity;
            responseEntity = httpRequestService.makePOSTHTTPCallUsingHMAC(
                    "RECREATION",
                    "get_resident",
                    mainPath + newLegalEntity,
                    "recreationKey",
                    reqBody
            );
            return responseEntity;
        } catch (RestException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Server not available!");
        }
    }

    /**
     * @param serviceName
     * @param description
     * @param amount
     * @param citizenCardNumber
     * @param redirectUrl
     * @return
     */
    public ResponseEntity<?> makePayment(String serviceName, String description, double
            amount, String citizenCardNumber, String redirectUrl) {

        String reqBody = "{" +
                "\"serviceName\":\"" + serviceName + "\"," +
                "\"description\":\"" + description + "\"," +
                "\"amount\":" + amount + "," +
                "\"citizenCardNumber\":" + citizenCardNumber + "," +
                "\"redirectUrl\":\"" + redirectUrl + "\"" +
                "}";

        try {
            ResponseEntity<?> responseEntity;
            responseEntity = httpRequestService.makePOSTHTTPCallUsingHMAC(
                    "RECREATION",
                    "payment",
                    paymentPath,
                    "recreationKey",
                    reqBody
            );
            return responseEntity;
        } catch (RestException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Server not available!");
        }
    }

    /**
     * @param citizenId
     * @return responseEntity which came from City Management Module
     */
    public ResponseEntity<?> checkUserFromCityManagement(String citizenId) {
        try {
            ResponseEntity<?> responseEntity;
            responseEntity = httpRequestService.makeGETHTTPCallUsingHMAC(
                    "RECREATION",
                    "get_resident",
                    mainPath + citizenPath + citizenId,
                    "recreationKey"
            );
            return responseEntity;
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Citizen Id not found!", false));
        }
    }
}
