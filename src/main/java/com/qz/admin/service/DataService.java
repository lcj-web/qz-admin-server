package com.qz.admin.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qz.admin.common.SeedBundle;
import com.qz.admin.entity.Consult;
import com.qz.admin.entity.Country;
import com.qz.admin.entity.Policy;
import com.qz.admin.entity.User;
import com.qz.admin.repository.ConsultRepository;
import com.qz.admin.repository.CountryRepository;
import com.qz.admin.repository.PolicyRepository;
import com.qz.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 种子数据加载与重置：从 classpath:seed.json 读取初始业务数据，
 * 管理员账号（admin/editor，密码 123456）在代码中建立。
 */
@Service
public class DataService {

    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private PolicyRepository policyRepository;
    @Autowired
    private ConsultRepository consultRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ResourceLoader resourceLoader;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private SeedBundle loadSeedBundle() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:seed.json");
        return objectMapper.readValue(resource.getInputStream(), SeedBundle.class);
    }

    @Transactional
    public void seedDatabase() {
        try {
            SeedBundle bundle = loadSeedBundle();
            if (countryRepository.count() == 0) {
                countryRepository.saveAll(bundle.getCountries());
            }
            if (policyRepository.count() == 0) {
                policyRepository.saveAll(bundle.getPolicies());
            }
            if (consultRepository.count() == 0) {
                consultRepository.saveAll(bundle.getConsults());
            }
            if (userRepository.count() == 0) {
                userRepository.save(new User(null, "admin", "123456", "系统管理员", "admin", "admin@visamaster.com", ""));
                userRepository.save(new User(null, "editor", "123456", "内容运营", "editor", "editor@visamaster.com", ""));
            }
        } catch (IOException e) {
            throw new RuntimeException("初始化种子数据失败：" + e.getMessage(), e);
        }
    }

    @Transactional
    public void reset() {
        consultRepository.deleteAll();
        policyRepository.deleteAll();
        countryRepository.deleteAll();
        try {
            SeedBundle bundle = loadSeedBundle();
            countryRepository.saveAll(bundle.getCountries());
            policyRepository.saveAll(bundle.getPolicies());
            consultRepository.saveAll(bundle.getConsults());
        } catch (IOException e) {
            throw new RuntimeException("重置数据失败：" + e.getMessage(), e);
        }
    }

    public Object getCompanyInfo() {
        try {
            Resource resource = resourceLoader.getResource("classpath:seed.json");
            Map<String, Object> map = objectMapper.readValue(
                    resource.getInputStream(), new TypeReference<Map<String, Object>>() {
                    });
            return map.get("companyInfo");
        } catch (IOException e) {
            throw new RuntimeException("读取公司信息失败：" + e.getMessage(), e);
        }
    }

    public List<Country> seedCountries() throws IOException {
        return loadSeedBundle().getCountries();
    }
}
