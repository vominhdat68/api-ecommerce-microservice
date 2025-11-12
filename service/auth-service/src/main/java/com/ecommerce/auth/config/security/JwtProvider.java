package com.ecommerce.auth.config.security;

import com.ecommerce.auth.config.JwtConfigProperties;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtProvider {
    private final RSAPrivateKey privateKey;
    private final JwtConfigProperties jwtProperties;

    public JwtProvider(KeyPair keyPair, JwtConfigProperties jwtProperties) {
        this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
        this.jwtProperties = jwtProperties;
    }


//    String subject, List<String> roles, List<String> scopes
    public String generateAccessToken(UserDetails userDetails,String jti) {


        // 1. Tạo JWT claims
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .issuer(jwtProperties.getClaims().getIss()) // iss claim
                .subject("subject")                           // sub claim
                .audience(jwtProperties.getClaims().getAud()) // aud claim
                .expirationTime(Date.from(Instant.now().plusSeconds(3600))) // 1h expiry
                .claim(jwtProperties.getClaims().getCustomClaims().getRolesClaim(), userDetails.getAuthorities())
                .claim(jwtProperties.getClaims().getCustomClaims().getScopesClaim(), userDetails.getAuthorities())
                .issueTime(new Date())
                .build();

        // 2. Ký token bằng RS256
        JWSSigner signer = new RSASSASigner(privateKey);
        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader.Builder(JWSAlgorithm.RS256)
                        .keyID(UUID.randomUUID().toString()) // kid for key rotation
                        .build(),
                claims
        );

        try {
            signedJWT.sign(signer);
            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("Failed to sign JWT", e);
        }
    }

//    public String generateAccessToken(UserDetails userDetails,String jti) {
//        // các quyền user
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("sub", UUID.randomUUID().toString());
//        claims.put("email", userDetails.getUsername());
//        claims.put("roles", userDetails.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toList()));
//        claims.put("iss", "server1");
//        claims.put("jti", jti);
//        return Jwts.builder()
//                .setSubject(userDetails.getUsername())
//                .setClaims(claims)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis()+ accessTokenExpiration))
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();
//    }

    public String generateRefreshToken(){
        return UUID.randomUUID().toString();

    }

    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolve) {
//        final Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
//        return claimsResolve.apply(claims);
    return null;
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUserName(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
    public boolean isTokenExpired(String token){
        return extractClaim(token,Claims::getExpiration).before(new Date());
    }
    public String extractJtiToken(String token){
        return extractClaim(token,claims -> claims.get("jti",String.class));
    }
    public long extractExpirationToken(String token){
        return extractClaim(token,Claims::getExpiration).getTime();
    }
    public boolean isRefreshTokenValidForRequest(String token, HttpServletRequest servletRequest) {
        try {
            String ip_address = getClientIp(servletRequest);
            String extIpAddressToken = extractClaim(token,claims -> claims.get("ip_address",String.class));
            if(!extIpAddressToken.equals(ip_address))return false;

            String user_agent = servletRequest.getHeader("User-Agent");
            String extUserAgentToken = extractClaim(token,claims -> claims.get("user_agent",String.class));
            if(!extUserAgentToken.equals(user_agent))return false;

        } catch (AuthenticationException e) {
            // ghi log
            System.err.println("Validate Access Token>>>>>>>>>>>>> "+ e.getMessage());
            return false;
        }
        return true;
    }
    /**
     * Có proxy (ngang qua Nginx, Load Balancer...)	Dùng X-Forwarded-For
     * Không có proxy	Dùng request.getRemoteAddr()
     * */
    private String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader != null && !xfHeader.isEmpty()) {
            // X-Forwarded-For: clientIp, proxy1, proxy2,...
            return xfHeader.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

}
