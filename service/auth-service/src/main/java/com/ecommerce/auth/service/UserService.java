package com.ecommerce.auth.service;

import com.ecommerce.auth.cache.AuthCache;
import com.ecommerce.auth.dto.request.ChangePasswordRequest;
import com.ecommerce.auth.dto.request.RegisterRequest;
import com.ecommerce.auth.dto.request.ResetPasswordRequest;
import com.ecommerce.auth.dto.request.VerifyOtpRequest;
import com.ecommerce.auth.entity.User;
import com.ecommerce.auth.repository.UserRepository;
import com.ecommerce.user.response.ApiResponse;
import com.ecommerce.user.response.ResponseCode;
import com.ecommerce.user.util.OTPUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;
    private final AuthCache authCache;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public boolean exitsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Account not found"));
    }

    public ApiResponse<Object> changePassword(ChangePasswordRequest request, String username) {

        Optional<User> otUser = userRepository.findByUsername(username);
        if(otUser.isEmpty()){
            return ApiResponse.error(ResponseCode.USER_NOT_FOUND.getCode(), ResponseCode.USER_NOT_FOUND.getEnMessage());
        }
        User user = otUser.get();
        if(!passwordEncoder.matches(request.currentPassword(),user.getPassword())){
            return ApiResponse.error(ResponseCode.CHANGE_PASSWORD_WRONG_OLD.getCode(), ResponseCode.CHANGE_PASSWORD_WRONG_OLD.getEnMessage());
        }
        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
        return ApiResponse.success(ResponseCode.CHANGE_PASSWORD_SUCCESS.getEnMessage());
    }
    public ApiResponse<Object> forgotPassword(String email) {
        if(!userRepository.existsByEmail(email)){
            return ApiResponse.error(ResponseCode.FORGOT_PASSWORD_EMAIL_NOT_FOUND.getCode(), ResponseCode.FORGOT_PASSWORD_EMAIL_NOT_FOUND.getEnMessage());
        }
        String otp = OTPUtil.generateSecureOtp();
        otpService.sendOtpVerifyForgotPass(email,otp);
        return ApiResponse.success(null);
    }

    public ApiResponse<Object> resetPassword(ResetPasswordRequest request) {
       String email = authCache.getForgotTokenVerify(request.token());
       Optional<User> otUser = userRepository.findByEmail(email);
       if(otUser.isEmpty()){
           return ApiResponse.error(ResponseCode.RESET_PASSWORD_TOKEN_EXPIRED.getCode(),ResponseCode.RESET_PASSWORD_TOKEN_EXPIRED.getEnMessage());
       }
       User user = otUser.get();
       user.setPassword(passwordEncoder.encode(request.newPassword()));
    return ApiResponse.success(ResponseCode.RESET_PASSWORD_SUCCESS.getEnMessage());
    }

    public ApiResponse<Object> verifyOtp(VerifyOtpRequest request) {
//        if (!authCache.isOtpResetPass(request.email(),request.otp())){
//            return ApiResponse.error(ResponseCode.OTP_VERIFICATION_FAILED.getCode(), ResponseCode.OTP_VERIFICATION_FAILED.getEnMessage());
//        }
        // tao token cho phien nhap lai mat khau moi
        String tokenResetPassword = UUID.randomUUID().toString();
        authCache.putForgotTokenVerify(tokenResetPassword,request.email());
        return ApiResponse.success(tokenResetPassword);
    }

    public User createPendingUser(RegisterRequest request) {
        return new User(
                request.username(),
                request.email(),
                passwordEncoder.encode(request.password()),
                false
        );
    }

    public boolean validPassword(String requestPassword, String userPassword) {
        return passwordEncoder.matches(requestPassword,userPassword);
    }
}