package com.management.user.security;

import com.management.user.dto.RoleDto;
import com.management.user.dto.UserDto;
import com.management.user.service.RoleService;
import com.management.user.service.UserService;
import com.netflix.discovery.converters.Auto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenHelper {
    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    public String generateToken(UserDto user, RoleDto role) {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        Date expirationDate = calendar.getTime();

        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .subject(user.getUserId())
                .issuedAt(now)
                .expiration(expirationDate)
                .claim("name", user.getName())
                .claim("email", user.getName())
                .claim("role", role.getName())
                .signWith(key).compact();
    }

    public boolean validateTokenAndItsClaims(String token, List<String> allowedRoles) {
        try {
            // Empty allowed roles list means every role is allowed - no authorization needed
            if (allowedRoles.isEmpty()) {
                return true;
            }
            var role = getClaimsFromToken(token).get("role", String.class);
            return allowedRoles.contains(role);
        }
        catch (Exception ex) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        var claims = getClaimsFromToken(token);
        return claims.getSubject();
    }

    private Claims getClaimsFromToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
