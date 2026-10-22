package com.qz.admin.service;

import com.qz.admin.common.DateUtil;
import com.qz.admin.common.LoginRequest;
import com.qz.admin.common.LoginResult;
import com.qz.admin.common.PasswordRequest;
import com.qz.admin.common.Result;
import com.qz.admin.common.TokenUtil;
import com.qz.admin.common.UserInfo;
import com.qz.admin.entity.User;
import com.qz.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public Result<LoginResult> login(LoginRequest req) {
        if (req.getUsername() == null || req.getUsername().trim().isEmpty()
                || req.getPassword() == null || req.getPassword().trim().isEmpty()) {
            return Result.error("请输入账号和密码");
        }
        Optional<User> opt = userRepository.findByUsername(req.getUsername().trim());
        User user = opt.orElse(null);
        if (user == null || !user.getPassword().equals(req.getPassword())) {
            return Result.error("账号或密码错误");
        }
        String token = TokenUtil.generate(user.getId());
        user.setLastLoginAt(DateUtil.now());
        userRepository.save(user);
        return Result.success(new LoginResult(token, toUserInfo(user)));
    }

    public Result<UserInfo> getUserInfo(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return Result.error("登录状态已失效，请重新登录");
        }
        return Result.success(toUserInfo(user));
    }

    public Result<Void> updatePassword(Long userId, PasswordRequest req) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return Result.error("账号不存在");
        }
        if (req.getOldPassword() == null || !user.getPassword().equals(req.getOldPassword())) {
            return Result.error("原密码输入不正确");
        }
        if (req.getNewPassword() == null || req.getNewPassword().length() < 6) {
            return Result.error("新密码长度不能少于 6 位");
        }
        user.setPassword(req.getNewPassword());
        userRepository.save(user);
        return Result.success(null);
    }

    public UserInfo toUserInfo(User u) {
        return new UserInfo(u.getId(), u.getUsername(), u.getNickname(),
                u.getRole(), u.getEmail(), u.getLastLoginAt());
    }
}
