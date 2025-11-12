package com.ecommerce.auth.service;

import com.ecommerce.auth.cache.AuthCache;
import com.ecommerce.user.cache.Redis.RedisKeys;
import com.ecommerce.user.email.service.EmailSenderService;
import com.ecommerce.user.email.dto.request.SendEmailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OtpService  {
    private final EmailSenderService senderService;
    private final AuthCache authCache;


    @Async
    public void sendOtpEmailRegisterAsync(String email,String otp) {
//        String content_html = templateEngine.process("otp-email",
//                new Context().setVariable("otp", request.getOtp()));

        try {
        senderService.sendEmail(SendEmailRequest.builder()
                .to(email)
                .subject("Your OTP Register Code")
                .content("Your OTP is: " + otp)
                .isHtml(false)
                .build());
        } catch (Exception e) {
            // Rollback cache nếu gửi email thất bại
            authCache.rollbackRegistrationCache(email);
        }


    }

    @Async
    public void sendOtpVerifyForgotPass(String email, String otp) {
        senderService.sendEmail(SendEmailRequest.builder()
                .to(email)
                .subject("Your OTP Verify Forgot Password")
                .content(String.format("Code new OTP: <b>%s</b> (expire %d minutes)", otp, RedisKeys.OTP_EXPIRE_TTL.toMinutes()))
                .isHtml(false)
                .build());
    }
}
