package com.qz.admin.controller;

import com.qz.admin.common.PageResult;
import com.qz.admin.common.Result;
import com.qz.admin.common.TopRequest;
import com.qz.admin.entity.Policy;
import com.qz.admin.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    @GetMapping
    public Result<PageResult<Policy>> list(@RequestParam(required = false) String keyword,
                                          @RequestParam(required = false) String category,
                                          @RequestParam(required = false) String status,
                                          @RequestParam(defaultValue = "1") int page,
                                          @RequestParam(defaultValue = "10") int pageSize) {
        return Result.success(policyService.page(keyword, category, status, page, pageSize));
    }

    @GetMapping("/categories")
    public Result<List<String>> categories() {
        return Result.success(policyService.categories());
    }

    @PostMapping
    public Result<Policy> create(@RequestBody Policy payload) {
        return Result.success(policyService.create(payload));
    }

    @PutMapping("/{id}")
    public Result<Policy> update(@PathVariable String id, @RequestBody Policy payload) {
        return Result.success(policyService.update(id, payload));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable String id) {
        policyService.delete(id);
        return Result.success(null);
    }

    @PatchMapping("/{id}/top")
    public Result<Void> top(@PathVariable String id, @RequestBody TopRequest req) {
        policyService.toggleTop(id, Boolean.TRUE.equals(req.getTop()));
        return Result.success(null);
    }
}
