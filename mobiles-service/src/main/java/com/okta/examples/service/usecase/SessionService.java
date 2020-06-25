package com.okta.examples.service.usecase;

import com.okta.examples.repository.SessionRepository;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
@MapperScan("com.okta.examples.repository")
@Service
public class SessionService {

    @Autowired
    SessionRepository repository;

    public String checkSession(String idUser){
        return repository.checkSession(idUser);
    }

    public void startSession(String idUser, String idSession){
        repository.startSession(idUser, encryptPassword(idSession) );
    }

    public void destroySession(String idUser){
        repository.destroySession(idUser);
    }

    public Integer checkSessionExpired(String idUser, String idSession){ return repository.checkSessionExpired(idUser, idSession);}

    public Integer checkSessionExpiredWithoutSession(String idUser){ return repository.checkSessionExpiredWithoutSession(idUser);}

    private String encryptPassword(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
