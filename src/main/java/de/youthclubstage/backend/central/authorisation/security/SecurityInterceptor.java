package de.youthclubstage.backend.central.authorisation.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SecurityInterceptor implements HandlerInterceptor {

    public static final String AUTH_PATH = "/secure";
    private static final String AUTH_HEADER = "Authorisation";

    @Autowired
    private TokenValidationService tokenValidationService;

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(request.getRequestURI().contains(AUTH_PATH)) {
            String token = request.getHeader(AUTH_HEADER);
            return tokenValidationService.validateToken(token);
        } else {
            return false;
        }

    }

}
