package com.qz.admin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qz.admin.common.TokenUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Token 拦截器：从 Authorization: Bearer <token> 或 query 参数 token 解析 userId。
 * 校验失败直接返回 401 且 success=false，与前端统一响应一致。
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 判断是否为无需登录的公开接口。
     * - 登录、公司信息：任何方法均放行（后台管理使用）
     * - 其余接口：均需携带有效 Token（文档 §3.2）
     * - 匿名提交咨询：POST /api/consults/public 放行（H5 公众站使用）
     */
    private boolean isPublic(String path, String method) {
        if ("/api/auth/login".equals(path) || "/api/system/company".equals(path)) {
            return true;
        }
        if ("POST".equals(method) && "/api/consults/public".equals(path)) {
            return true;
        }
        // 文档 §3.2：除登录与公司信息接口外，其余接口均需登录，此处不再额外放行只读 GET
        return false;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();
        String method = request.getMethod();
        if (isPublic(path, method)) {
            return true;
        }

        String auth = request.getHeader("Authorization");
        String token = null;
        if (auth != null && auth.startsWith("Bearer ")) {
            token = auth.substring(7);
        }
        if (token == null) {
            token = request.getParameter("token");
        }

        Long userId = TokenUtil.parseUserId(token);
        if (userId == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("success", false);
            body.put("message", "登录已失效，请重新登录");
            body.put("data", null);
            response.getWriter().write(objectMapper.writeValueAsString(body));
            return false;
        }

        request.setAttribute("userId", userId);
        return true;
    }
}
