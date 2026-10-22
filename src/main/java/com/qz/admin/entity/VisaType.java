package com.qz.admin.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 签证类型（国家详情中的签证办理种类），作为值对象以 JSON 形式持久化。
 */
@Data
@NoArgsConstructor
public class VisaType {

    private String name;
    private String crowd;
    private String stay;
    private Integer price;
}
