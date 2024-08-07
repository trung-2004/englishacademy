package com.englishacademy.EnglishAcademy.config;

import com.englishacademy.EnglishAcademy.services.impl.JWTServiceImpl;
import com.englishacademy.EnglishAcademy.services.impl.UserServiceImpl;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTServiceImpl jwtServiceImpl;
    @Autowired
    private UserServiceImpl userService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        //final String jwt;
        //final String userEmail;

        if (StringUtils.isEmpty(authHeader) || !org.apache.commons.lang3.StringUtils.startsWith(authHeader, "Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String jwt = authHeader.substring(7);
            String userEmail = jwtServiceImpl.extractUsername(jwt);
            if (userEmail == null || !jwtServiceImpl.isTokenValid(jwt, userService.userDetailsService().loadUserByUsername(userEmail))) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return;
            }

            if (StringUtils.isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);

                if (jwtServiceImpl.isTokenValid(jwt, userDetails)){
                    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );

                    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    securityContext.setAuthentication(token);

                    SecurityContextHolder.setContext(securityContext);

                }
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }
        filterChain.doFilter(request,response);
    }
}

