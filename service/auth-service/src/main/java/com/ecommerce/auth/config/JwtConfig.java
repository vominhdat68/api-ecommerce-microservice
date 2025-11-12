package com.ecommerce.auth.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.encrypt.KeyStoreKeyFactory;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;


//Cấu hình JWKSource (Java KeyStore + JWK Set)
@Configuration
public class JwtConfig {

    /**
     * 1. Tải cặp khóa RSA từ Java KeyStore (JKS)
     */
    @Bean
    public KeyPair keyPair() {
        ClassPathResource ksFile = new ClassPathResource("auth-keystore.jks");
        KeyStoreKeyFactory ksFactory = new KeyStoreKeyFactory(ksFile, "yourpassword".toCharArray());
        return ksFactory.getKeyPair("auth-key");
    }

    /**
     * 2. Tạo JWKSource (Nguồn cung cấp JSON Web Key Set - JWKS)
     * - Chuyển đổi KeyPair thành RSAKey (định dạng JWK)
     * - Tạo JWKSet chứa public key để expose qua endpoint /jwks
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource(KeyPair keyPair) {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        // Xây dựng RSAKey với Key ID (kid) ngẫu nhiên
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();

        // JWKSet chứa public key (dùng cho /jwks endpoint)
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    /**
     * 3. Cấu hình JwtDecoder để xác thực JWT
     * - Sử dụng JWKSource ở trên để lấy public key verify chữ ký
     */
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }
}
// bash
//    keytool -genkeypair -alias auth-key -keyalg RSA -keysize 2048 -keystore
//    auth-keystore.jks -storepass yourpassword -keypass yourpassword
