package com.qz.admin.service;

import com.qz.admin.common.DateUtil;
import com.qz.admin.common.PageResult;
import com.qz.admin.entity.Country;
import com.qz.admin.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public PageResult<Country> page(String keyword, String continent, int page, int pageSize) {
        List<Country> all = countryRepository.findAll().stream()
                .filter(c -> match(c, keyword, continent))
                .sorted(Comparator.comparing(Country::getHot, Comparator.reverseOrder())
                        .thenComparing(Country::getPrice, Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toList());
        return paginate(all, page, pageSize);
    }

    public List<Country> all() {
        return countryRepository.findAll().stream()
                .sorted(Comparator.comparing(Country::getHot, Comparator.reverseOrder())
                        .thenComparing(Country::getPrice, Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toList());
    }

    public List<String> continents() {
        return countryRepository.findAll().stream()
                .map(Country::getContinent)
                .filter(Objects::nonNull)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public Country create(Country payload) {
        if (payload.getName() == null || payload.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("请填写国家/地区名称");
        }
        boolean dup = countryRepository.findAll().stream()
                .anyMatch(c -> c.getName().equals(payload.getName()));
        if (dup) {
            throw new IllegalArgumentException("该国家/地区已存在");
        }
        payload.setId(genId("country"));
        payload.setUpdatedAt(DateUtil.now());
        return countryRepository.save(payload);
    }

    public Country update(String id, Country payload) {
        Country existing = countryRepository.findById(id).orElse(null);
        if (existing == null) {
            throw new IllegalArgumentException("数据不存在或已被删除");
        }
        if (payload.getName() != null && !payload.getName().equals(existing.getName())) {
            boolean dup = countryRepository.findAll().stream()
                    .anyMatch(c -> c.getName().equals(payload.getName()) && !c.getId().equals(id));
            if (dup) {
                throw new IllegalArgumentException("该国家/地区已存在");
            }
        }
        payload.setId(id);
        payload.setUpdatedAt(DateUtil.now());
        return countryRepository.save(payload);
    }

    public void delete(String id) {
        if (!countryRepository.existsById(id)) {
            throw new IllegalArgumentException("数据不存在或已被删除");
        }
        countryRepository.deleteById(id);
    }

    private boolean match(Country c, String keyword, String continent) {
        if (continent != null && !continent.isEmpty() && !continent.equals(c.getContinent())) {
            return false;
        }
        if (keyword == null || keyword.trim().isEmpty()) {
            return true;
        }
        String k = keyword.trim().toLowerCase();
        return (c.getName() != null && c.getName().toLowerCase().contains(k))
                || (c.getEn() != null && c.getEn().toLowerCase().contains(k))
                || (c.getTag() != null && c.getTag().toLowerCase().contains(k));
    }

    private PageResult<Country> paginate(List<Country> all, int page, int pageSize) {
        int total = all.size();
        int from = (page - 1) * pageSize;
        if (from > total) {
            from = total;
        }
        int to = Math.min(from + pageSize, total);
        return new PageResult<>(all.subList(from, to), total, page, pageSize);
    }

    private String genId(String prefix) {
        return prefix + "-" + Long.toString(System.nanoTime(), 36) + "-" + UUID.randomUUID().toString().substring(0, 4);
    }
}
