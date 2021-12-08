package com.epam.recreation_module.controller;

import com.epam.recreation_module.model.DTO.UserDTO;
import com.epam.recreation_module.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Class {@code CommentaryController} is an endpoint of the API which used for Users
 * Annotated by {@link RestController} with no parameters to provide answer in application/json
 * Annotated by {@link RequestMapping} with parameter value =  "/user"
 * Annotated by {@link CrossOrigin} with no parameters to <em>give permission to connection</em>
 * So that {@code UserController} is accessed by sending requests to /user
 *
 * @author Yulchiev Abbos
 * @since 1.0
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get a Recreation by Recreation's ID method
     * Annotated by {@link ApiOperation} with parameters:
     * value =  "Get user's list by page"
     * tags = "auth-controller"
     * Annotated by {@link GetMapping} with parameter value = /all
     *
     * @param page User's page
     * @param size User page's size
     * @return <em>HTTP</em> response with User list
     */
    @ApiOperation(value = "Get user's list by page", tags = {"auth-controller"})
    @GetMapping("/all")
    public ResponseEntity<?> getAll(@ApiParam(value = "Page's size") @RequestParam(defaultValue = "0", required = false) Integer page,
                                    @ApiParam(value = "Page's size") @RequestParam(defaultValue = "20", required = false) Integer size) {
        return userService.getAllUsers(page, size);
    }

    /**
     * Get a Recreation by Recreation's ID method
     * Annotated by {@link ApiOperation} with parameters:
     * value =  "Get a user by user's ID"
     * tags = "auth-controller"
     * Annotated by {@link GetMapping} with parameter value = /{userId}
     *
     * @param userId User ID
     * @return <em>HTTP</em> response with a User
     */
    @ApiOperation(value = "Get a user by user's ID", tags = {"auth-controller"})
    @GetMapping("/{userId}")
    public ResponseEntity<?> getById(@ApiParam(name = "User ID", type = "Long", example = "1")
                                     @PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    /**
     * Add a new User with role method
     * Annotated by {@link ApiOperation} with parameters:
     * value =  "This method creates a new User with role (for Admins)"
     * tags = "auth-controller"
     * Annotated by {@link PostMapping} with parameter value = /register
     *
     * @param userDTO Object for create a new User
     * @return <em>HTTP</em> response with message
     */
    @ApiOperation(value = "This method creates a new User with role (for Admins)", tags = {"auth-controller"})
    @PostMapping("/register")
    public ResponseEntity<?> register(@ApiParam(name = "User", value = "User's body", type = "object") @RequestBody UserDTO userDTO) {
        return userService.register(userDTO);
    }

    /**
     * Edit a User by User's ID method
     * Annotated by {@link ApiOperation} with parameters:
     * value =  "This method edits a  User with User's ID"
     * tags = "auth-controller"
     * Annotated by {@link PutMapping} with parameter value = /edit/{id}
     *
     * @param id      User's ID
     * @param userDTO Object for edit a User
     * @return <em>HTTP</em> response whit message
     */
    @ApiOperation(value = "This method edits a  User with User's ID", tags = {"auth-controller"})
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> edit(@ApiParam(name = "User ID", type = "Long", example = "1") @PathVariable Long id, @RequestBody UserDTO userDTO) {
        return userService.editUser(id, userDTO);
    }

    /**
     * Delete a User by User's ID method
     * Annotated by {@link ApiOperation} with parameters:
     * value =  "This method deletes a User with User's ID"
     * tags = "auth-controller"
     * Annotated by {@link DeleteMapping} with parameter value = delete/{id}
     *
     * @param id User's ID
     * @return <em>HTTP</em> response whit message
     */
    @ApiOperation(value = "This method deletes a User with User's ID", tags = {"auth-controller"})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@ApiParam(name = "User ID", type = "Long", example = "1") @PathVariable Long id) {
        return userService.deleteUser(id);
    }

    /**
     * Delete a User by User's ID method
     * Annotated by {@link ApiOperation} with parameters:
     * value =  "This method deletes a User with User's ID"
     * tags = "auth-controller"
     * Annotated by {@link DeleteMapping} with parameter value = delete/{id}
     *
     * @param id User's ID
     * @return <em>HTTP</em> response whit message
     */
    @ApiOperation(value = "This method actives a User with User's ID", tags = {"auth-controller"})
    @PostMapping("/deactivateUser/{id}")
    public ResponseEntity<?> activateUser(@ApiParam(name = "User ID", type = "Long", example = "1") @PathVariable Long id) {
        return userService.activateUser(id);
    }
}
