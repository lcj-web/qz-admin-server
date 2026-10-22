package com.qz.admin.controller;

import com.qz.admin.common.LoginRequest;
import com.qz.admin.common.LoginResult;
import com.qz.admin.common.PasswordRequest;
import com.qz.admin.common.Result;
import com.qz.admin.common.UserInfo;
import com.qz.admin.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Result<LoginResult> login(@RequestBody LoginRequest req) {
        return authService.login(req);
    }

    @GetMapping("/userinfo")
    public Result<UserInfo> userinfo(@RequestAttribute("userId") Long userId) {
        return authService.getUserInfo(userId);
    }

    @PostMapping("/logout")
    public Result<Void> logout() {
        return Result.success(null);
    }

    @PutMapping("/password")
    public Result<Void> password(@RequestAttribute("userId") Long userId,
                                @RequestBody PasswordRequest req) {
        return authService.updatePassword(userId, req);
    }
}
