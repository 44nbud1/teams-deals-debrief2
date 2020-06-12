//package com.okta.examples.adapter.jwt;
//
//import com.okta.examples.adapter.exception.SessionException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//public class JwtRequestFilter extends OncePerRequestFilter {
//
//    @Autowired
//    JwtUserDetailsService jwtUserDetailsService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws ServletException, IOException {
//
////        final String requestTokenHeader = request.getHeader("Authorization");
//        String header = request.getHeader("Authorization");
//        if (header == null || !header.startsWith("Bearer ")){
//            throw new SessionException("You are not authorized.", HttpStatus.UNAUTHORIZED);
//        }
//        String[] split = request.getServletPath().split("/");
//        String idUser = split[3];
//        // JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
////        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
////            jwtToken = requestTokenHeader.substring(7);
////            try {
////                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
////            } catch (IllegalArgumentException e) {
////                System.out.println("Unable to get JWT Token");
////            } catch (ExpiredJwtException e) {
////                System.out.println(e.getMessage());
////            } catch (MalformedJwtException e){
////                System.out.println(e.getLocalizedMessage());
////            }
////        }
//
//        // Once we get the token validate it.
//        // if token is valid configure Spring Security to manually set authentication
////        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
////            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
////
////            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
////                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
////                        userDetails, null, userDetails.getAuthorities());
////                usernamePasswordAuthenticationToken
////                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//////
//////        // After setting the Authentication in the context, we specify that the current user is authenticated.
//////        // So it passes the Spring Security Configurations successfully.
////                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
////            }
////        }
//        UserDetails userDetails = this.jwtUserDetailsService.loadBySession(header, idUser);
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//                userDetails, null, userDetails.getAuthorities());
//        usernamePasswordAuthenticationToken
//                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
////         After setting the Authentication in the context, we specify that the current user is authenticated.
////         So it passes the Spring Security Configurations successfully.
//        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//        chain.doFilter(request, response);
//    }
//}