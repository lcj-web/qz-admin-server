package com.qz.admin.config;

import com.qz.admin.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 应用启动后初始化种子数据（仅在数据为空时写入，保证重启不覆盖手工数据）。
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private DataService dataService;

    @Override
    public void run(String... args) {
        dataService.seedDatabase();
    }
}
