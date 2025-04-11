package com.ecommerce.auth;

import com.ecommerce.auth.security.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class JwtProviderTest {
    @InjectMocks //
    private JwtProvider jwtProvider;
    @Mock
    private UserDetails userDetails;
    private static final String SECRET ="mySecretKey1234567890mySecretKey1234567890";
    private static final long EXPIRATION_MS = 3600000; // 1 hour

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        jwtProvider = new JwtProvider();
        jwtProvider.secret = SECRET;
        jwtProvider.init();

        // Mock UserDetails
        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_USER")
        );
        when(userDetails.getUsername()).thenReturn("testUser");
//        when(userDetails.getAuthorities()).thenReturn(authorities);
    }

    @Test
    public void testGenerateToken(){
        String token = jwtProvider.generateToken(userDetails,EXPIRATION_MS);

        assertNotNull(token);

        // Check JWT format (header.payload.signature)
        assertTrue(token.split("\\.").length ==3);

    }

    @Test
    public void testExtractUsername(){
        String token = jwtProvider.generateToken(userDetails,EXPIRATION_MS);
        assertEquals("testUser",jwtProvider.extractUserName(token));
    }

    @Test
    public void testIsTokenValid_ValidToken(){
        String token = jwtProvider.generateToken(userDetails,EXPIRATION_MS);
        assertTrue(jwtProvider.isTokenValid(token,userDetails));
    }

    @Test
    public void testIsTokenExpired_Expired() throws InterruptedException {
        // Arrange
        String token = jwtProvider.generateToken(userDetails, 1); // 1ms expiration

        // Wait for token to expire
        Thread.sleep(2);

        // Act & Assert
        assertTrue(jwtProvider.isTokenExpired(token));
    }

}
