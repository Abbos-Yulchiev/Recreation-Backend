package com.epam.recreation_module.service.impl;

import com.epam.recreation_module.model.DTO.LoginDTO;
import com.epam.recreation_module.model.Role;
import com.epam.recreation_module.model.User;
import com.epam.recreation_module.payload.ApiResponse;
import com.epam.recreation_module.payload.UserInfo;
import com.epam.recreation_module.repository.UserRepository;
import com.epam.recreation_module.security.JwtProvider;
import com.epam.recreation_module.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class AuthServiceImpl implements UserDetailsService, AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    /**
     * @param userRepository
     * @param authenticationManager
     * @param jwtProvider
     */
    public AuthServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    /**
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found!"));
    }

    /**
     * @param loginDTO
     * @return
     */
    @Override
    public ResponseEntity<?> login(LoginDTO loginDTO) {

        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(),
                    loginDTO.getPassword()
            ));
            User user = (User) authenticate.getPrincipal();
            String token = jwtProvider.generateToken(user.getUsername());
            List<String> roles = getRoleList(user.getRoles());
            if (user.isAccountNonLocked()
                    && user.isAccountNonExpired()
                    && user.isCredentialsNonExpired()
                    && !user.isDeleted()) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse("Successfully logged!", true, roles, token));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User expired");
            }
        } catch (BadCredentialsException badCredentialsException) {
            throw new BadCredentialsException("Username or password is wrong!");
        }
    }

    /**
     * @param user
     * @return
     */
    @Override
    public ResponseEntity<?> authToken(User user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse("Unauthorized", false));
        }
        UserInfo userInfo = UserInfo.builder()
                .username(user.getUsername())
                .roles(user.getRoles()).build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse("Success!", true, userInfo));
    }

    /**
     * @param roles
     * @return
     */
    private List<String> getRoleList(Set<Role> roles) {
        List<String> roleList = new ArrayList<>();
        for (Role role : roles) {
            roleList.add(role.getName());
        }
        return roleList;
    }
}
