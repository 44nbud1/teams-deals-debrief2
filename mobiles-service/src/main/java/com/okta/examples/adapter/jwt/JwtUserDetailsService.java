package com.okta.examples.adapter.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new org.springframework.security.core.userdetails.User(username,
                "$2a$10$0Ii1DG4cXE2lSKHiWTNtbOs0ZUAEXSzXPBtzGJQXtX0lzCjgHX3ue",
                new ArrayList<>());
    }

}
