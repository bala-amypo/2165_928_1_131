package com.example.demo.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Custom Authentication token used after JWT validation.
 * This token is stored in SecurityContext once JWT is verified.
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;
    private final String token;

    public JwtAuthenticationToken(
            Object principal,
            String token,
            Collection<? extends GrantedAuthority> authorities
    ) {
        super(authorities);
        this.principal = principal;
        this.token = token;
        setAuthenticated(true); // important
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
