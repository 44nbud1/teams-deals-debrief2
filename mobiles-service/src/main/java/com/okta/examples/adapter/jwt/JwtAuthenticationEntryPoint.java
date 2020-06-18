package com.okta.examples.adapter.jwt;

import com.okta.examples.model.response.ResponseFailed;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -7858869558953243875L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse resp,
                         AuthenticationException authException) throws IOException {
//        response.sendError(21, "You are not authorized");
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        resp.getWriter().print(ResponseFailed.unAuthorized(request.getServletPath()).getBody());
//        response.setStatus(Integer.parseInt(DealsStatus.NOT_AUTHORIZED.getValue()));
//        throw new UnauthorizedException("Unauthorized", HttpStatus.UNAUTHORIZED);
//        throw new SessionException("You are not authorized", HttpStatus.UNAUTHORIZED);
    }
}

