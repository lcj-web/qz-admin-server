package com.qz.admin.service;

import com.qz.admin.common.DashboardData;
import com.qz.admin.common.DashboardStats;
import com.qz.admin.common.NameCount;
import com.qz.admin.entity.Consult;
import com.qz.admin.entity.Country;
import com.qz.admin.entity.Policy;
import com.qz.admin.repository.ConsultRepository;
import com.qz.admin.repository.CountryRepository;
import com.qz.admin.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private PolicyRepository policyRepository;
    @Autowired
    private ConsultRepository consultRepository;

    public DashboardData getDashboard() {
        List<Country> countries = countryRepository.findAll();
        List<Policy> policies = policyRepository.findAll();
        List<Consult> consults = consultRepository.findAll();

        long countryTotal = countries.size();
        long policyTotal = policies.size();
        long consultTotal = consults.size();
        long pendingTotal = consults.stream().filter(c -> "pending".equals(c.getStatus())).count();
        long topPolicyTotal = policies.stream().filter(p -> Boolean.TRUE.equals(p.getTop())).count();
        long visTypeTotal = countries.stream()
                .mapToInt(c -> c.getTypes() == null ? 0 : c.getTypes().size())
                .sum();

        DashboardData data = new DashboardData();
        data.setStats(new DashboardStats(countryTotal, policyTotal, consultTotal,
                pendingTotal, topPolicyTotal, visTypeTotal));
        data.setContinentStats(countBy(countries, Country::getContinent));
        data.setCategoryStats(countBy(policies, Policy::getCategory));
        data.setStatusStats(countBy(consults, Consult::getStatus));
        data.setHotCountries(countries.stream()
                .sorted(Comparator.comparing(Country::getHot, Comparator.reverseOrder()))
                .limit(5)
                .collect(Collectors.toList()));
        data.setRecentConsults(consults.stream()
                .sorted(Comparator.comparing(Consult::getCreateTime, Comparator.reverseOrder()))
                .limit(6)
                .collect(Collectors.toList()));
        data.setLatestPolicies(policies.stream()
                .sorted(Comparator.comparing(Policy::getDate, Comparator.reverseOrder()))
                .limit(5)
                .collect(Collectors.toList()));
        return data;
    }

    private <T> List<NameCount> countBy(List<T> list, Function<T, String> getter) {
        return list.stream()
                .map(getter)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .map(e -> new NameCount(e.getKey(), e.getValue().intValue()))
                .sorted(Comparator.comparingInt(NameCount::getCount).reversed())
                .collect(Collectors.toList());
    }
}
