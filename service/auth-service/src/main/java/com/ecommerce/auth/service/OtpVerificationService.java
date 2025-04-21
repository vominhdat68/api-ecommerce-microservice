package com.ecommerce.auth.service;

import com.ecommerce.auth.dto.VerifyEmailRequest;
import com.ecommerce.auth.entity.User;
import com.ecommerce.auth.repository.UserRepository;
import com.ecommerce.shared_libs.cache.Redis.RedisCacheService;
import com.ecommerce.shared_libs.cache.Redis.RedisKeys;
import com.ecommerce.shared_libs.email.service.EmailSenderService;
import com.ecommerce.shared_libs.email.dto.request.SendEmailRequest;
import com.ecommerce.shared_libs.util.OTPUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class OtpVerificationService {
    private final UserRepository userRepository;
    private final EmailSenderService senderService;
    private final RedisCacheService redisCacheService;


    public void sendOtp(String email) {
//        String content_html = templateEngine.process("otp-email",
//                new Context().setVariable("otp", request.getOtp()));

        String OTP = OTPUtil.generateOtp();
        redisCacheService.save(RedisKeys.OTP_EMAIL.getPrefix()+email,OTP,RedisKeys.OTP_EMAIL.getTTL()); // cache redis
        senderService.sendEmail(SendEmailRequest.builder()
                .to(email)
                .subject("Your OTP Code")
                .content(OTP)
                .isHtml(false)
                .build());
    }

 public void verifyEmail(VerifyEmailRequest request){
     User user = userRepository.findByUsername(request.getEmail())
             .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));

     String KEY_EMAIL_CACHE = RedisKeys.OTP_EMAIL.getPrefix()+request.getEmail();
     Object OTP = redisCacheService.get(KEY_EMAIL_CACHE);
     if (OTP == null || !OTP.equals(request.getOtp())){
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid or expired OTP");
     }
     user.setEnabled(true);
     userRepository.save(user);
     redisCacheService.delete(KEY_EMAIL_CACHE);// xóa otp của email vừa xác thực
 }


}
