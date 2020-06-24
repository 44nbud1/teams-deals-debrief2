package com.okta.examples.service.validation;

import com.okta.examples.adapter.jwt.JwtTokenUtil;
import com.okta.examples.repository.SessionRepository;
import com.okta.examples.service.usecase.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class SessionValidation {

    @Autowired
    SessionRepository repository;

    @Autowired
    SessionService sessionService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    public boolean request(String idUser, HttpServletRequest request, String path){

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")){
            return false;
        }

        String session = header.substring(7);
        session  = jwtTokenUtil.getUsernameFromToken(session);
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

        String[] split = path.split("/");
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

//    public boolean requestVoucher(HttpServletRequest request){
//
//        String header = request.getHeader("Authorization");
//        if (header == null || !header.startsWith("Bearer ")){
//            return false;
//        }
//
//        String idSession = header.substring(7);
//        if (sessionService.checkSessionWithoutId(idSession) == 0){
//            return false;
//        }
//
//        String[] split = request.getServletPath().split("/");
//        String idUser = sessionService.getIdUserSession(idSession);
//        if (split[2].equalsIgnoreCase("user")) {
//            if (idUser.equals("12") || idUser.equals("13")) {
//                return false;
//            }
//        }else if (split[2].equalsIgnoreCase("admin")){
//            if (!idUser.equals("12")) {
//                if (!idUser.equals("13")) {
//                    return false;
//                }
//            }
//        }
//
//        if (sessionService.checkSessionExpiredWithoutId(idSession) == 0){
//            sessionService.destroySessionWithoutId(idSession);
//            return false;
//        }
//
//        return true;
//    }
//
//    public boolean requestAdmin(HttpServletRequest request){
//
//        String header = request.getHeader("Authorization");
//        if (header == null || !header.startsWith("Bearer ")){
//            return false;
//        }
//
//        String idSession = header.substring(7);
//        if (sessionService.checkSessionWithoutId(idSession) == 0){
//            return false;
//        }
//        if (!sessionService.getIdUserSession(idSession).equals("12") ||
//                !sessionService.getIdUserSession(idSession).equals("13")){
//            return false;
//        }
//
//        if (sessionService.checkSessionExpiredWithoutId(idSession) == 0){
//            sessionService.destroySessionWithoutId(idSession);
//            return false;
//        }
//        return true;
//    }
//
    public boolean requestId(HttpServletRequest request){

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")){
            return false;
        }

        String session = header.substring(7);
        session  = jwtTokenUtil.getUsernameFromToken(session);

        if (session.length() < 38){
            return false;
        }

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
        if (request.getServletPath().isEmpty()){
            return false;
        }
        String[] split = request.getServletPath().split("/");

        if (split[2].equalsIgnoreCase("user")) {
            if (idUser.equals("12") || idUser.equals("13") || idUser.equals("126")) {
                return false;
            }
        }else if (split[2].equalsIgnoreCase("admin")){
            if (!idUser.equals("12")) {
                if (!idUser.equals("13")) {
                    if (!idUser.equals("126")) {
                        return false;
                    }
                }
            }
        }

        if (sessionService.checkSessionExpired(idUser, idSession) == 0){
            sessionService.destroySession(idUser);
            return false;
        }

        return true;
    }

    private boolean decode(String password, String passhass) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, passhass);
    }

    public boolean requestSession(String idUser, HttpServletRequest request){

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")){
            return false;
        }

        String session = header.substring(7);
        session  = jwtTokenUtil.getUsernameFromToken(session);

        if (session.length() < 38){
            return false;
        }

//        String idUser = session.substring(36);

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

//        String[] split = request.getServletPath().split("/");
//        if (split[2].equalsIgnoreCase("user")) {
//            if (idUser.equals("12") || idUser.equals("13") || idUser.equals("126")) {
//                return false;
//            }
//        }else if (split[2].equalsIgnoreCase("admin")){
//            if (!idUser.equals("12")) {
//                if (!idUser.equals("13")) {
//                    if (!idUser.equals("126")) {
//                        return false;
//                    }
//                }
//            }
//        }

        if (sessionService.checkSessionExpired(idUser, idSession) == 0){
            sessionService.destroySession(idUser);
            return false;
        }

        return true;
    }
    public boolean checkSession(String path, String username){

        String session = username;
        if (session.length() < 38){
            return false;
        }

        String idUser = session.substring(36);
        String idSession = sessionService.checkSession(idUser);
        if (idSession == null){
            return false;
        }

        if (!decode(session, idSession)){
            return false;
        }

//        String[] split = path.split("/");
//        if (split[2].equalsIgnoreCase("user")) {
//            if (idUser.equals("12") || idUser.equals("13") || idUser.equals("126")) {
//                return false;
//            }
//        }else if (split[2].equalsIgnoreCase("admin")){
//            if (!idUser.equals("12")) {
//                if (!idUser.equals("13")) {
//                    if (!idUser.equals("126")) {
//                        return false;
//                    }
//                }
//            }
//        }

        if (sessionService.checkSessionExpired(idUser, idSession) == 0){
            sessionService.destroySession(idUser);
            return false;
        }

        return true;
    }
}
