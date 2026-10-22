package com.qz.admin.service;

import com.qz.admin.common.DateUtil;
import com.qz.admin.common.PageResult;
import com.qz.admin.entity.Policy;
import com.qz.admin.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PolicyService {

    @Autowired
    private PolicyRepository policyRepository;

    public PageResult<Policy> page(String keyword, String category, String status, int page, int pageSize) {
        List<Policy> all = policyRepository.findAll().stream()
                .filter(p -> match(p, keyword, category, status))
                .sorted((a, b) -> {
                    int d = safeCompare(b.getDate(), a.getDate());
                    if (d != 0) {
                        return d;
                    }
                    return Boolean.compare(Boolean.TRUE.equals(b.getTop()), Boolean.TRUE.equals(a.getTop()));
                })
                .collect(Collectors.toList());
        return paginate(all, page, pageSize);
    }

    public List<String> categories() {
        return policyRepository.findAll().stream()
                .map(Policy::getCategory)
                .filter(Objects::nonNull)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public Policy create(Policy payload) {
        if (payload.getTitle() == null || payload.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("请填写政策标题");
        }
        payload.setId(genId("policy"));
        payload.setUpdatedAt(DateUtil.now());
        return policyRepository.save(payload);
    }

    public Policy update(String id, Policy payload) {
        if (!policyRepository.existsById(id)) {
            throw new IllegalArgumentException("数据不存在或已被删除");
        }
        payload.setId(id);
        payload.setUpdatedAt(DateUtil.now());
        return policyRepository.save(payload);
    }

    public void delete(String id) {
        if (!policyRepository.existsById(id)) {
            throw new IllegalArgumentException("数据不存在或已被删除");
        }
        policyRepository.deleteById(id);
    }

    public void toggleTop(String id, boolean top) {
        Policy p = policyRepository.findById(id).orElse(null);
        if (p == null) {
            throw new IllegalArgumentException("数据不存在或已被删除");
        }
        p.setTop(top);
        p.setUpdatedAt(DateUtil.now());
        policyRepository.save(p);
    }

    private boolean match(Policy p, String keyword, String category, String status) {
        if (category != null && !category.isEmpty() && !category.equals(p.getCategory())) {
            return false;
        }
        if (status != null && !status.isEmpty() && !status.equals(p.getStatus())) {
            return false;
        }
        if (keyword == null || keyword.trim().isEmpty()) {
            return true;
        }
        String k = keyword.trim().toLowerCase();
        return (p.getTitle() != null && p.getTitle().toLowerCase().contains(k))
                || (p.getSummary() != null && p.getSummary().toLowerCase().contains(k));
    }

    private int safeCompare(String a, String b) {
        if (a == null && b == null) {
            return 0;
        }
        if (a == null) {
            return -1;
        }
        if (b == null) {
            return 1;
        }
        return a.compareTo(b);
    }

    private PageResult<Policy> paginate(List<Policy> all, int page, int pageSize) {
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
