package com.qz.admin.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.qz.admin.entity.Consult;
import com.qz.admin.entity.Country;
import com.qz.admin.entity.Policy;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于反序列化 resources/seed.json 的顶层结构（仅取业务数据，其余忽略）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeedBundle {

    private List<Country> countries = new ArrayList<>();
    private List<Policy> policies = new ArrayList<>();
    private List<Consult> consults = new ArrayList<>();
}
