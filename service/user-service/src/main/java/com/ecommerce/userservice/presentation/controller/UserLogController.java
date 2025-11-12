package com.ecommerce.userservice.presentation.controller;

import com.ecommerce.userservice.application.dto.response.UserLogResponse;
import com.ecommerce.userservice.domain.service.UserLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/logs")
public class UserLogController {

    private final UserLogService userLogService;

    @GetMapping
    public List<UserLogResponse> getLogs() {

        return null;
    }
}
