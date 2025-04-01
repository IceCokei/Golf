package org.example.golf.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class RequestLoggingFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();
        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            String requestUri = request.getRequestURI();
            String method = request.getMethod();
            int status = responseWrapper.getStatus();
            
            // 获取当前认证用户
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication != null ? authentication.getName() : "anonymous";
            
            // 获取用户角色
            String roles = "";
            if (authentication != null && authentication.getAuthorities() != null) {
                roles = authentication.getAuthorities().toString();
            }
            
            // 记录请求信息
            logger.info("API访问: {} {} - 状态: {} - 用户: {} - 角色: {} - 耗时: {}ms",
                    method, requestUri, status, username, roles, duration);
            
            // 记录请求头信息
            if (logger.isDebugEnabled()) {
                Map<String, String> headers = getRequestHeaders(request);
                logger.debug("请求头: {}", headers);
            }
            
            // 记录请求参数
            if (logger.isDebugEnabled() && !requestUri.contains("/auth")) {
                Map<String, String[]> parameterMap = request.getParameterMap();
                logger.debug("请求参数: {}", parameterMap);
            }
            
            // 记录响应状态
            if (status >= 400) {
                logger.warn("请求失败: {} {} - 状态: {} - 用户: {}", method, requestUri, status, username);
            }
        }
        
        // 复制响应内容到原始响应
        responseWrapper.copyBodyToResponse();
    }
    
    private Map<String, String> getRequestHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            if (!"authorization".equalsIgnoreCase(headerName)) {
                headers.put(headerName, request.getHeader(headerName));
            } else {
                headers.put(headerName, "******");
            }
        }
        return headers;
    }
} 