package com.epam.recreation_module.controller;

import com.epam.recreation_module.model.DTO.UserDTO;
import com.epam.recreation_module.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Get user's list by page", tags = {"auth-controller"})
    @GetMapping("/all")
    public ResponseEntity<?> getAll(@ApiParam(value = "Page's size") @RequestParam(defaultValue = "0", required = false) Integer page,
                                    @ApiParam(value = "Page's size") @RequestParam(defaultValue = "20", required = false) Integer size) {
        return userService.getAllUsers(page, size);
    }

    @ApiOperation(value = "Get a user list by user ID", tags = {"auth-controller"})
    @GetMapping("/{userId}")
    public ResponseEntity<?> getById(@ApiParam(name = "User ID", type = "Long", example = "1") @PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @ApiOperation(value = "This method creates a new User with role (for Admins)", tags = {"auth-controller"})
    @PostMapping("/register")
    public ResponseEntity<?> register(@ApiParam(name = "User", value = "User's body", type = "object") @RequestBody UserDTO userDTO) {
        return userService.register(userDTO);
    }

    @ApiOperation(value = "This method edits a  User with User's ID", tags = {"auth-controller"})
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> edit(@ApiParam(name = "User ID", type = "Long", example = "1") @PathVariable Long id, @RequestBody UserDTO userDTO) {
        return userService.editUser(id, userDTO);
    }

    @ApiOperation(value = "This method deletes a User with User's ID", tags = {"auth-controller"})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@ApiParam(name = "User ID", type = "Long", example = "1") @PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @ApiOperation(value = "This method actives a User with User's ID", tags = {"auth-controller"})
    @PostMapping("/deactivateUser/{id}")
    public ResponseEntity<?> activateUser(@ApiParam(name = "User ID", type = "Long", example = "1") @PathVariable Long id) {
        return userService.activateUser(id);
    }
}
