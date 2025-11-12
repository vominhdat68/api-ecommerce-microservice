package com.ecommerce.auth.config;

// File: auth-service/src/main/java/com/yourcompany/auth/config/JwtConfigProperties.java
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfigProperties {
    private Claims claims;

    @Data
    public static class Claims {
        private String iss;
        private String aud;
        private CustomClaims customClaims;

        @Data
        public static class CustomClaims {
            private String rolesClaim;
            private String scopesClaim;
        }
    }
}
