package com.qz.admin.controller;

import com.qz.admin.common.Result;
import com.qz.admin.entity.User;
import com.qz.admin.repository.UserRepository;
import com.qz.admin.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/system")
public class SystemController {

    @Autowired
    private DataService dataService;
    @Autowired
    private UserRepository userRepository;

    /** 公开接口：公司信息（登录页 slogan 使用），无需登录 */
    @GetMapping("/company")
    public Result<Object> company() {
        return Result.success(dataService.getCompanyInfo());
    }

    /** 重置业务数据为初始种子（仅管理员） */
    @PostMapping("/reset")
    public Result<Void> reset(@RequestAttribute("userId") Long userId) {
        User u = userRepository.findById(userId).orElse(null);
        if (u == null || !"admin".equals(u.getRole())) {
            return Result.error("仅管理员可重置数据");
        }
        dataService.reset();
        return Result.success(null);
    }
}
