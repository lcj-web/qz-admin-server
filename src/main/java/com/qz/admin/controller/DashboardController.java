package com.qz.admin.controller;

import com.qz.admin.common.DashboardData;
import com.qz.admin.common.Result;
import com.qz.admin.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public Result<DashboardData> dashboard() {
        return Result.success(dashboardService.getDashboard());
    }
}
