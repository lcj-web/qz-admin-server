package com.qz.admin.entity;

import com.qz.admin.config.StringListConverter;
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
@Table(name = "qz_policy")
@Data
@NoArgsConstructor
public class Policy {

    @Id
    private String id;

    private String title;
    private String date;
    private String category;
    private String source;
    private Boolean top;
    private String status;
    private String summary;

    @Convert(converter = StringListConverter.class)
    @Column
    private List<String> content = new ArrayList<>();

    private String updatedAt;
}
