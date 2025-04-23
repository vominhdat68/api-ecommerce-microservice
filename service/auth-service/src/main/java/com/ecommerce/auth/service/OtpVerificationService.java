package com.ecommerce.auth.service;

import com.ecommerce.auth.dto.request.VerifyEmailRequest;
import com.ecommerce.auth.entity.User;
import com.ecommerce.shared_libs.cache.Redis.OTPCache;
import com.ecommerce.shared_libs.email.service.EmailSenderService;
import com.ecommerce.shared_libs.email.dto.request.SendEmailRequest;
import com.ecommerce.shared_libs.response.ApiResponse;
import com.ecommerce.shared_libs.response.ResponseCode;
import com.ecommerce.shared_libs.util.OTPUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OtpVerificationService {
    private final UserService userService;
    private final EmailSenderService senderService;
    private final OTPCache otpCache;


    public ApiResponse<Object> sendOtp(String email) {
//        String content_html = templateEngine.process("otp-email",
//                new Context().setVariable("otp", request.getOtp()));if
        if(!otpCache.isEmailRegistered(email)){
            return ApiResponse.error(ResponseCode.EMAIL_NOT_FOUND.getCode(), ResponseCode.EMAIL_NOT_FOUND.getEnMessage());
        }
        String OTP = OTPUtil.generateOtp();
        otpCache.cacheOtp(email,OTP);
        senderService.sendEmail(SendEmailRequest.builder()
                .to(email)
                .subject("Your OTP Code")
                .content(OTP)
                .isHtml(false)
                .build());
        return ApiResponse.success(null);
    }

 public ApiResponse<Object> verifyEmail(VerifyEmailRequest request){
      // check ttl email 1 hour
        if(!otpCache.isEmailRegistered(request.email())){
         return ApiResponse.error(ResponseCode.EMAIL_NOT_FOUND.getCode(), ResponseCode.EMAIL_NOT_FOUND.getEnMessage());
     }
     User user = userService.findByEmail(request.email());
     if (user == null) {
         return ApiResponse.error(ResponseCode.EMAIL_NOT_FOUND.getCode(),
                 ResponseCode.EMAIL_NOT_FOUND.getEnMessage());
     }
     if (!otpCache.validateOtp(request.email(), request.otp())){
         return ApiResponse.error(ResponseCode.OTP_VERIFICATION_FAILED.getCode(), ResponseCode.OTP_VERIFICATION_FAILED.getEnMessage());
     }

     if (!user.isEnabled()) {
         user.setEnabled(true);
         userService.saveUser(user);
     }
     // Clear OTP
     otpCache.removeEmailCache(request.email());
     otpCache.clearOtp(request.email());
     return ApiResponse.success(null);
 }


}
