package com.epam.recreation_module.security;

import com.epam.recreation_module.service.impl.AuthServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    final JwtProvider jwtProvider;
    final AuthServiceImpl authService;
    final String auth = "Authorization";
    final String bearer = "Bearer ";

    public JwtFilter(JwtProvider jwtProvider, AuthServiceImpl authService) {
        this.jwtProvider = jwtProvider;
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(auth);
        if (token != null && token.startsWith(bearer)) {
            token = token.substring(7);
            boolean validateToken = jwtProvider.validateToken(token);
            if (validateToken){
                String usernameFromToken = jwtProvider.getUsernameFromToken(token);
                UserDetails userDetails = authService.loadUserByUsername(usernameFromToken);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
