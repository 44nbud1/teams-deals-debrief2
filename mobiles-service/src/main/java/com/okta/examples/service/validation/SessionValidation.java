package com.okta.examples.service.validation;

import com.okta.examples.repository.MyBatisRepository;
import com.okta.examples.service.usecase.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

//        if(!idSession.equals(session)){
//            return false;
//        }

        if (!decode(session, idSession)){
            return false;
        }

        String[] split = request.getServletPath().split("/");
        if (split[2].equalsIgnoreCase("user")) {
            if (idUser.equals("12") || idUser.equals("13")) {
                return false;
            }
        }else if (split[2].equalsIgnoreCase("admin")){
            if (!idUser.equals("12")) {
                if (!idUser.equals("13")) {
                    return false;
                }
            }
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

        String[] split = request.getServletPath().split("/");
        String idUser = sessionService.getIdUserSession(idSession);
        if (split[2].equalsIgnoreCase("user")) {
            if (idUser.equals("12") || idUser.equals("13")) {
                return false;
            }
        }else if (split[2].equalsIgnoreCase("admin")){
            if (!idUser.equals("12")) {
                if (!idUser.equals("13")) {
                    return false;
                }
            }
        }

        if (sessionService.checkSessionExpiredWithoutId(idSession) == 0){
            sessionService.destroySessionWithoutId(idSession);
            return false;
        }

        return true;
    }

    public boolean requestAdmin(HttpServletRequest request){

        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")){
            return false;
        }

        String idSession = header.substring(7);
        if (sessionService.checkSessionWithoutId(idSession) == 0){
            return false;
        }
        if (!sessionService.getIdUserSession(idSession).equals("12") ||
                !sessionService.getIdUserSession(idSession).equals("13")){
            return false;
        }

        if (sessionService.checkSessionExpiredWithoutId(idSession) == 0){
            sessionService.destroySessionWithoutId(idSession);
            return false;
        }
        return true;
    }

    public boolean requestId(HttpServletRequest request){

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")){
            return false;
        }

        String session = header.substring(7);
        String idUser = session.substring(36);
        String idSession = sessionService.checkSession(idUser);
        if (idSession == null){
            return false;
        }

//        if(!idSession.equals(session)){
//            return false;
//        }

        if (!decode(session, idSession)){
            return false;
        }

        String[] split = request.getServletPath().split("/");
        if (split[2].equalsIgnoreCase("user")) {
            if (idUser.equals("12") || idUser.equals("13")) {
                return false;
            }
        }else if (split[2].equalsIgnoreCase("admin")){
            if (!idUser.equals("12")) {
                if (!idUser.equals("13")) {
                    return false;
                }
            }
        }

        if (sessionService.checkSessionExpired(idUser, idSession) == 0){
            sessionService.destroySession(idUser);
            return false;
        }

        return true;
    }

    public boolean decode(String password, String passhass) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, passhass);
    }
}
