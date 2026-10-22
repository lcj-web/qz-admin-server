package com.qz.admin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "qz_consult")
@Data
@NoArgsConstructor
public class Consult {

    @Id
    private String id;

    private String orderNo;
    private String createTime;

    @Column(name = "from_channel")
    private String from;

    private String name;
    private String phone;
    private String wechat;
    private String country;
    private String visaType;
    private String purpose;
    private String travelDate;
    private String people;
    private String city;
    private String passport;
    private String refused;

    @Column
    private String remark;

    private String status;
}
