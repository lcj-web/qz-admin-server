package com.qz.admin.controller;

import com.qz.admin.common.PageResult;
import com.qz.admin.common.Result;
import com.qz.admin.common.StatusRequest;
import com.qz.admin.entity.Consult;
import com.qz.admin.service.ConsultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/consults")
public class ConsultController {

    @Autowired
    private ConsultService consultService;

    /**
     * 匿名提交咨询（H5 公众站调用，无需登录）。
     */
    @PostMapping("/public")
    public Result<Consult> createPublic(@RequestBody Consult consult) {
        return Result.success(consultService.create(consult));
    }

    @GetMapping
    public Result<PageResult<Consult>> list(@RequestParam(required = false) String keyword,
                                           @RequestParam(required = false) String status,
                                           @RequestParam(defaultValue = "1") int page,
                                           @RequestParam(defaultValue = "10") int pageSize) {
        return Result.success(consultService.page(keyword, status, page, pageSize));
    }

    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(@PathVariable String id, @RequestBody StatusRequest req) {
        consultService.updateStatus(id, req.getStatus());
        return Result.success(null);
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable String id) {
        consultService.delete(id);
        return Result.success(null);
    }
}
