package com.example.simple_ecommerce.filter;

import com.example.simple_ecommerce.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String token = null;
        String userName =  null;

        if(header != null && header.startsWith("Bearer ")){
            token = header.substring(7);
            userName= jwtUtil.extractToken(token);
        }

        if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
            if(jwtUtil.validateToken(token)){
                UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(userName, null, null);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            }
        }

        filterChain.doFilter(request, response);
    }
}
