package com.epam.recreation_module.service.impl;

import com.epam.recreation_module.exception.NotFoundException;
import com.epam.recreation_module.exception.RestException;
import com.epam.recreation_module.model.DTO.UserDTO;
import com.epam.recreation_module.model.DTO.user.CityResponse;
import com.epam.recreation_module.model.Role;
import com.epam.recreation_module.model.User;
import com.epam.recreation_module.payload.ApiResponse;
import com.epam.recreation_module.repository.RoleRepository;
import com.epam.recreation_module.repository.UserRepository;
import com.epam.recreation_module.service.UserService;
import com.google.gson.Gson;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@PropertySource("classpath:url.properties")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ExternalConnections externalConnections;


    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder, @Lazy ExternalConnections externalConnections) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.externalConnections = externalConnections;
    }

    @Override
    public ResponseEntity<?> getAllUsers(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAll(pageable);
        return ResponseEntity.ok().body(userPage);
    }

    @Override
    public ResponseEntity<?> getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found", id));
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @Override
    public ResponseEntity<?> register(UserDTO userDTO) {

        Optional<User> optionalUser = userRepository.findByUsername(userDTO.getUsername());
        if (optionalUser.isPresent())
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Username already exist!");

        User newUser = new User();
        return userSetter(newUser, userDTO);
    }

    @Override
    public ResponseEntity<?> editUser(Long id, UserDTO userDTO) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RestException("User not found", HttpStatus.NOT_FOUND));
        boolean username = userRepository.existsByUsernameAndIdNot(userDTO.getUsername(), id);
        if (username)
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Username already existed!");
        return userSetter(user, userDTO);
    }

    @Override
    public ResponseEntity<?> deleteUser(Long id) {

        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found!", id));
        user.setDeleted(true);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse("User Successfully deleted!", true));
    }

    @Override
    public ResponseEntity<?> activateUser(Long id) {

        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Invalid User Id"));
        user.setDeleted(false);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("User deactivated!", true));
    }

    private ResponseEntity<?> userSetter(User user, UserDTO userDTO) {

        try {
            ResponseEntity<?> responseEntity = externalConnections.checkUserFromCityManagement(userDTO.getCitizenId());
            boolean exists = userRepository.existsByCitizenId(userDTO.getCitizenId());
            if (exists) return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("This citizen already registered!");
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                if (!userDTO.getPassword().equals(userDTO.getPrePassword()))
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body("PrePassword not match!");

                String citizenInfo = (String) responseEntity.getBody();
                Gson gson = new Gson();
                CityResponse cityResponse = gson.fromJson(citizenInfo, CityResponse.class);

                Role role;
                if (userDTO.getRole() == null) {
                    role = roleRepository.findByName("USER");
                } else {
                    role = roleRepository.findByName(userDTO.getRole());
                }
                user.setRoles(Collections.singleton(role));
                user.setCitizenId(userDTO.getCitizenId());
                user.setUsername(userDTO.getUsername());
                user.setFirstName(cityResponse.result.getFirstName());
                user.setLastName(cityResponse.result.getLastName());
                user.setEnabled(cityResponse.result.isActive());
                user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

                System.out.println(user);
                userRepository.save(user);
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(userDTO.getUsername());
            } else {
                return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Error occurred!");
        }
    }

    @Override
    public User findByLogin(String login) {
        try {
            return userRepository.findByUsername(login)
                    .orElseThrow(() -> new NotFoundException("User not logged!"));
        } catch (NotFoundException e) {
            return new User();
        }
    }

    @Override
    public User findCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        return findByLogin(userDetails.getUsername());
    }
}
