package com.okta.examples.service.validation;

import com.okta.examples.repository.MyBatisRepository;
import com.okta.examples.service.usecase.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class SessionValidation {

    @Autowired
    MyBatisRepository repository;

    @Autowired
    SessionService sessionService;

    public boolean request(String idUser, HttpServletRequest request){

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")){
            return false;
        }

        String session = header.substring(7);
        String idSession = sessionService.checkSession(idUser);

        if (idSession == null){
            return false;
        }

        if(!idSession.equals(session)){
            return false;
        }

        if (sessionService.checkSessionExpired(idUser, idSession) == 0){
            sessionService.destroySession(idUser);
            return false;
        }

        return true;
    }

    public boolean requestVoucher(HttpServletRequest request){

        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")){
            return false;
        }

        String idSession = header.substring(7);
        if (sessionService.checkSessionWithoutId(idSession) == 0){
            return false;
        }

        if (sessionService.checkSessionExpiredWithoutId(idSession) == 0){
            sessionService.destroySessionWithoutId(idSession);
            return false;
        }

        return true;
    }

}
