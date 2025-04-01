package org.example.golf.security;

import org.example.golf.model.Log;
import org.example.golf.repository.LogRepository;
import org.example.golf.security.event.AuthorizationFailureEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SecurityAuditListener {
    private static final Logger logger = LoggerFactory.getLogger(SecurityAuditListener.class);

    @Autowired
    private LogRepository logRepository;

    @EventListener
    public void authenticationSuccess(AuthenticationSuccessEvent event) {
        Authentication auth = event.getAuthentication();
        String username = auth.getName();
        String remoteAddress = extractRemoteAddress(auth);

        logger.info("用户登录成功: {} - IP: {}", username, remoteAddress);

        Log log = new Log();
        log.setType("LOGIN");
        log.setOperation("登录成功");
        log.setUsername(username);
        log.setIpAddress(remoteAddress);
        log.setCreatedAt(LocalDateTime.now());
        logRepository.save(log);
    }

    @EventListener
    public void authenticationFailed(AbstractAuthenticationFailureEvent event) {
        String username = event.getAuthentication().getName();
        String remoteAddress = extractRemoteAddress(event.getAuthentication());
        String errorMessage = event.getException().getMessage();

        logger.warn("用户登录失败: {} - IP: {} - 原因: {}", username, remoteAddress, errorMessage);

        Log log = new Log();
        log.setType("LOGIN_FAILED");
        log.setOperation("登录失败: " + errorMessage);
        log.setUsername(username);
        log.setIpAddress(remoteAddress);
        log.setCreatedAt(LocalDateTime.now());
        logRepository.save(log);
    }

    @EventListener
    public void accessDenied(AuthorizationFailureEvent event) {
        Authentication auth = event.getAuthentication();
        String username = auth.getName();

        logger.warn("访问被拒绝: {} - 权限不足", username);

        Log log = new Log();
        log.setType("ACCESS_DENIED");
        log.setOperation("访问被拒绝: 权限不足");
        log.setUsername(username);
        log.setCreatedAt(LocalDateTime.now());
        logRepository.save(log);
    }

    private String extractRemoteAddress(Authentication authentication) {
        if (authentication.getDetails() instanceof WebAuthenticationDetails) {
            WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
            return details.getRemoteAddress();
        }
        return "unknown";
    }
}