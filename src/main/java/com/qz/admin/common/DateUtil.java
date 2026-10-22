package com.qz.admin.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 时间工具：统一输出 yyyy-MM-dd HH:mm:ss。
 */
public class DateUtil {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String now() {
        return LocalDateTime.now().format(FORMATTER);
    }
}
