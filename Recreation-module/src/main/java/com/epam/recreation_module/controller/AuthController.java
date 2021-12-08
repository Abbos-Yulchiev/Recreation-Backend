package com.epam.recreation_module.controller;

import com.epam.recreation_module.model.DTO.LoginDTO;
import com.epam.recreation_module.model.User;
import com.epam.recreation_module.security.CurrentUser;
import com.epam.recreation_module.service.impl.AuthServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;


/**
 * Class {@code AuthController} is used for login and authorize the user's token
 * Annotated by {@link RestController} with no parameters to provide response in json format
 * Annotated by {@link RequestMapping} with parameter value = "/authorization"
 * {@code AuthController} is accessed by sending request to /authorization
 *
 * @author Abbos Yulchiev
 * @since 1.0
 */

@RestController
@RequestMapping("/authorization")
@CrossOrigin
public class AuthController {

    final AuthServiceImpl authService;

    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    /**
     * Login method
     * Annotated by {@link ApiOperation} with parameters:
     * value = Login with valid credentials
     * notes = Provide a LoginDTO to retrieve a token
     * response = String.class
     * Annotated by {@link PostMapping} with parameter value = /login
     *
     * @param loginDTO loginDTO consist of username and password
     * @return If username and password are correct return HTTP Response with token
     * else return "Username or password is wrong!" message with Unauthorized status
     */
    @ApiOperation(value = "Login with valid credentials",
            notes = "Provide a LoginDTO to retrieve a token")
    @PostMapping("/login")
    public ResponseEntity<?> login(@ApiParam(name = "LoginDTO", type = "json") @Valid @RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(authService.login(loginDTO));
    }


    /**
     * authToken method
     * Annotated by (@link  @ApiIgnore)
     * Annotated by {@link ApiOperation} with parameters:
     * value = Endpoint for authenticate JWT token
     * Annotated by {@link GetMapping} with parameter value = /authToken
     *
     * @param user user is current user token
     * @return <em>HTTP</em> response  with "message"
     */
    @ApiIgnore
    @ApiOperation(value = "Endpoint for authenticate JWT token")
    @GetMapping("/authToken")
    public ResponseEntity<?> authToken(@ApiParam("Token object") @CurrentUser User user) {
        return ResponseEntity.ok(authService.authToken(user));
    }
}
