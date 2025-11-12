package com.ecommerce.auth.controller;

import com.ecommerce.auth.dto.request.ChangePasswordRequest;
import com.ecommerce.auth.dto.request.ForgotPasswordRequest;
import com.ecommerce.auth.dto.request.ResetPasswordRequest;
import com.ecommerce.auth.dto.request.VerifyOtpRequest;
import com.ecommerce.auth.service.UserService;
import com.ecommerce.user.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    /***
     * api private -> auto check token access in OncePerRequestFilter
     *  uu tien trai nghiem nguoi dung, khong tu dong logout sau khi doi mat khau
     *  hay vao do se thuc hien xac thuc qua OTP hoac Email
     *          gui OTP den email
     *          nguoi dung nhap otp de hoan tat hay doi mat khau ma ko can dang nhap lai
     */
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request, Authentication authentication){
        ApiResponse<Object> response = userService.changePassword(request,authentication.getName());
        if(!response.isSuccess()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.message());
        }
        return  ResponseEntity.status(HttpStatus.OK).body(response.data());
    }
/**
 *Nhận thông tin gmail lay lai mat khau
 * gui OTP den email
 *
 * */
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody @Valid ForgotPasswordRequest request){
       ApiResponse<Object> response = userService.forgotPassword(request.email());
       if(!response.isSuccess()){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.message());
       }
        return  ResponseEntity.noContent().build();
    }
    /**
     * Xac thuc kiem tra ma OTP
     *
     * */
    @PostMapping("/verify-otp")
    public ResponseEntity<Object> verifyOTP(@RequestBody @Valid VerifyOtpRequest request){
        ApiResponse<Object> response = userService.verifyOtp(request);
        if(!response.isSuccess()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.message());
        }
        return  ResponseEntity.status(HttpStatus.OK).body(response.data());
    }
    /**
     *Tao lai mat khau moi
     *
     * */
    @PostMapping("/reset-password")
    public ResponseEntity<Object> resetPassword(@RequestBody @Valid ResetPasswordRequest request){
        ApiResponse<Object> response = userService.resetPassword(request);
        if(!response.isSuccess()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.message());
        }
        return  ResponseEntity.status(HttpStatus.OK).body(response.data());
    }
}
