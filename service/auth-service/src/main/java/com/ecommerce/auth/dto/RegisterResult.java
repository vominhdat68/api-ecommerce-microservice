package com.ecommerce.auth.dto;

import com.ecommerce.auth.enumMessage.RegisterStatus;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegisterResult {
    private RegisterStatus status;
    private String message;
}
