package com.epam.recreation_module.service;

import com.epam.recreation_module.model.DTO.UserDTO;
import com.epam.recreation_module.model.User;
import org.springframework.http.ResponseEntity;

public interface UserService {

    User findByLogin(String login);

    User findCurrentUser();

    ResponseEntity<?> getUserById(Long id);

    ResponseEntity<?> getAllUsers(Integer page, Integer size);

    ResponseEntity<?> register(UserDTO userDTO);

    ResponseEntity<?> editUser(Long id, UserDTO userDTO);

    ResponseEntity<?> deleteUser(Long id);

    ResponseEntity<?> activateUser(Long id);
}
