package com.qz.admin.controller;

import com.qz.admin.common.PageResult;
import com.qz.admin.common.Result;
import com.qz.admin.entity.Country;
import com.qz.admin.service.CountryService;
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

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping
    public Result<PageResult<Country>> list(@RequestParam(required = false) String keyword,
                                           @RequestParam(required = false) String continent,
                                           @RequestParam(defaultValue = "1") int page,
                                           @RequestParam(defaultValue = "10") int pageSize) {
        return Result.success(countryService.page(keyword, continent, page, pageSize));
    }

    @GetMapping("/all")
    public Result<List<Country>> all() {
        return Result.success(countryService.all());
    }

    @GetMapping("/continents")
    public Result<List<String>> continents() {
        return Result.success(countryService.continents());
    }

    @PostMapping
    public Result<Country> create(@RequestBody Country payload) {
        return Result.success(countryService.create(payload));
    }

    @PutMapping("/{id}")
    public Result<Country> update(@PathVariable String id, @RequestBody Country payload) {
        return Result.success(countryService.update(id, payload));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable String id) {
        countryService.delete(id);
        return Result.success(null);
    }
}
