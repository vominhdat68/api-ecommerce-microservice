package com.ecommerce.auth.cache.metadata;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshTokenMetadata {

    private String username;
    private String jti;
    private String ip;
    private String userAgent;
    private String deviceId;
    private long issuedAt;
}
