package org.example.golf.security.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.security.core.Authentication;

public class AuthorizationFailureEvent extends ApplicationEvent {
    private final Authentication authentication;
    private final String message;

    public AuthorizationFailureEvent(Object source, Authentication authentication, String message) {
        super(source);
        this.authentication = authentication;
        this.message = message;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public String getMessage() {
        return message;
    }
} 