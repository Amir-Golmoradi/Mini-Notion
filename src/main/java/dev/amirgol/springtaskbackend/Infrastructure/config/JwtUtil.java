package dev.amirgol.springtaskbackend.Infrastructure.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
public class JwtUtil {
    /**
     * JWT secret key from application properties
     * Should be stored as environment variable in production
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * JWT token expiration time in milliseconds
     */
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * Refresh token expiration time in milliseconds
     */
    @Value("${jwt.refresh-expiration}")
    private Long refreshExpiration;

    /**
     * Generate signing key from secret
     * @return SecretKey for JWT signing
     */
    private SecretKey getSigningKey() {
        // Create a secure key from the secret string
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Extract username from JWT token
     * @param token JWT token
     * @return username from token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extract expiration date from JWT token
     * @param token JWT token
     * @return expiration date
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extract specific claim from JWT token
     * @param token JWT token
     * @param claimsResolver function to extract specific claim
     * @return extracted claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extract all claims from JWT token
     * @param token JWT token
     * @return all claims
     */
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
            throw e;
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
            throw e;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw e;
        } catch (SecurityException | IllegalArgumentException e) {
            log.error("JWT token validation error: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Check if JWT token is expired
     * @param token JWT token
     * @return true if expired, false otherwise
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Generate JWT access token for user
     * @param userDetails user details
     * @return generated JWT token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        // Add additional claims if needed (roles, permissions, etc.)
        claims.put("type", "access");
        return createToken(claims, userDetails.getUsername(), expiration);
    }

    /**
     * Generate refresh token for user
     * @param userDetails user details
     * @return generated refresh token
     */
    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "refresh");
        return createToken(claims, userDetails.getUsername(), refreshExpiration);
    }

    /**
     * Create JWT token with claims, subject and expiration
     * @param claims additional claims
     * @param subject token subject (username)
     * @param expirationTime expiration time in milliseconds
     * @return created JWT token
     */
    private String createToken(Map<String, Object> claims, String subject, Long expirationTime) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Validate JWT token against user details
     * @param token JWT token
     * @param userDetails user details
     * @return true if valid, false otherwise
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (JwtException | IllegalArgumentException e) {
            log.error("JWT token validation failed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Validate JWT token without user details
     * @param token JWT token
     * @return true if valid, false otherwise
     */
    public Boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            log.error("JWT token validation failed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Check if token is refresh token
     * @param token JWT token
     * @return true if refresh token, false otherwise
     */
    public Boolean isRefreshToken(String token) {
        try {
            String type = extractClaim(token, claims -> claims.get("type", String.class));
            return "refresh".equals(type);
        } catch (Exception e) {
            return false;
        }
    }
}
