package com.qz.admin.entity;

import com.qz.admin.config.StringListConverter;
import com.qz.admin.config.VisaTypeListConverter;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "qz_country")
@Data
@NoArgsConstructor
public class Country {

    @Id
    private String id;

    private String name;
    private String en;
    private String flag;
    private String continent;
    private Integer hot;
    private String tag;
    private Integer price;
    private String cycle;
    private String stay;
    private String entry;

    @Column(name = "description")
    private String desc;

    @Convert(converter = VisaTypeListConverter.class)
    @Column
    private List<VisaType> types = new ArrayList<>();

    @Convert(converter = StringListConverter.class)
    @Column
    private List<String> materials = new ArrayList<>();

    @Convert(converter = StringListConverter.class)
    @Column
    private List<String> notice = new ArrayList<>();

    private String updatedAt;
}
