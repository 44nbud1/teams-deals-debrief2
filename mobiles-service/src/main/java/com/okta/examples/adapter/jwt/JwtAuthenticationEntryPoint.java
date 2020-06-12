//package com.okta.examples.adapter.jwt;
//
//import com.okta.examples.adapter.exception.SessionException;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.Serializable;
//
//@Component
//public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
//
//    private static final long serialVersionUID = -7858869558953243875L;
//
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response,
//                         AuthenticationException authException) {
//        //throw new UnauthorizedException("Unaothorized", HttpStatus.UNAUTHORIZED);
//        throw new SessionException("You are not authorized", HttpStatus.UNAUTHORIZED);
//    }
//}
//
