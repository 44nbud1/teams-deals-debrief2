package com.okta.examples.adapter.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

//    @Autowired
//    JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUserDetailsService userDetailsService;


//    public String generateToken(String tokenDetail, String id){
//        //auth(tokenDetail, password);
//        UserDetails userDetails = userDetailsService.loadUserById(tokenDetail, id);
//        return jwtTokenUtil.generateToken(userDetails);
//    }
//
//    public String authRegis(String tokenDetail, String id){
//        //auth(tokenDetail, password);
//        UserDetails userDetails = userDetailsService.loadUserById(tokenDetail, id);
//        return jwtTokenUtil.generateToken(userDetails);
//    }

    private void auth(String username, String password){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            try {
                throw new Exception("USER_DISABLED", e);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        catch (BadCredentialsException e) {
            try {
                throw new Exception("INVALID_CREDENTIALS", e);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

}
