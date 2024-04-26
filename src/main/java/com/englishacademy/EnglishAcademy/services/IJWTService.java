package com.englishacademy.EnglishAcademy.services;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface IJWTService {
    String extractUsername(String token);
    String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);
    String generateToken(UserDetails userDetails);
    String generateToken2(UserDetails userDetails);
    boolean isTokenValid(String token, UserDetails userDetails);
}
