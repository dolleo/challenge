package com.ldol.challenge.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class CustomAuthFilter extends GenericFilterBean {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "bearer";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper((HttpServletRequest) servletRequest);

        HttpServletRequest request = (HttpServletRequest) requestWrapper.getRequest();
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            String token = bearerToken.substring(BEARER_PREFIX.length() +1);
            if (token.equals("dGhlc2VjcmV0dG9rZW4=")) {
                GrantedAuthority authority = new SimpleGrantedAuthority("userRole");
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(token, token, Collections.singletonList(authority));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(requestWrapper, servletResponse);
    }
}
