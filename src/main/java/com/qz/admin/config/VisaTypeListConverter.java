package com.qz.admin.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qz.admin.entity.VisaType;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import java.util.ArrayList;
import java.util.List;

/**
 * 将 List<VisaType> 以 JSON 字符串形式持久化到数据库 TEXT 列。
 */
@Converter(autoApply = false)
public class VisaTypeListConverter implements AttributeConverter<List<VisaType>, String> {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<VisaType> list) {
        if (list == null) {
            return "[]";
        }
        try {
            return MAPPER.writeValueAsString(list);
        } catch (Exception e) {
            return "[]";
        }
    }

    @Override
    public List<VisaType> convertToEntityAttribute(String s) {
        if (s == null || s.trim().isEmpty()) {
            return new ArrayList<>();
        }
        try {
            return MAPPER.readValue(s, new TypeReference<List<VisaType>>() {
            });
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
