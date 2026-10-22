package com.qz.admin.common;

/**
 * Token 工具：与前端约定的格式 qz-token-{userId}-{timestamp}。
 * 演示环境为无状态 token，不做过期校验；生产可替换为 JWT。
 */
public class TokenUtil {

    public static String generate(Long userId) {
        return "qz-token-" + userId + "-" + System.currentTimeMillis();
    }

    public static Long parseUserId(String token) {
        if (token == null) {
            return null;
        }
        String[] parts = token.split("-");
        if (parts.length >= 4 && "qz".equals(parts[0]) && "token".equals(parts[1])) {
            try {
                return Long.parseLong(parts[2]);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }
}
