package com.solvd.onlinestore.web.security;

import com.solvd.onlinestore.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        String token = null;
        if(authHeader !=null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
        }
        if(token != null){
            final String userEmail = jwtService.extractClaim(token, Claims :: getSubject);
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() != null){
                final JwtUser user = jwtService.extractAllClaims(token);
                user.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(user);
            }
        }
        filterChain.doFilter(request, response);
    }

}
