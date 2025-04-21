package com.ecommerce.shared_libs.email.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendEmailRequest {
    String to;
    String subject;
    String content;
    boolean isHtml;
}
