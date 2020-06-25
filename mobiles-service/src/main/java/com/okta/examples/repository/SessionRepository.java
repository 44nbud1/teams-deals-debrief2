package com.okta.examples.repository;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository {

    // Mobile Domain

    final String startSession = "INSERT session(id_user, id_session, valid_date, status) " +
                                "values(#{idUser}, #{idSession}, date_add(now(), interval 7 day), 1)";
    @Insert(startSession)
    void startSession(@Param("idUser") String idUser, @Param("idSession") String idSession);

    final String checkSession = "SELECT id_session from session where id_user = #{idUser}" +
                                "and status = 1 order by created_at desc limit 1";
    @Select(checkSession)
    @Results(value = {
            @Result(property = "idSession", column = "id_session")
    })
    String checkSession(@Param("idUser") String idUser);

    final String destroySession = "UPDATE session set status = 0 where id_user = #{idUser}";
    @Update(destroySession)
    void destroySession(@Param("idUser") String idUser);

    final String checkSessionExpired = "SELECT count(*) as amount from session where id_user = #{idUser} " +
                                        "and id_session = #{idSession} and valid_date > now()";
    @Select(checkSessionExpired)
    @Results(value = {
            @Result(column = "amount")
    })
    Integer checkSessionExpired(@Param("idUser") String idUser, @Param("idSession") String idSession);

    final String checkSessionWithoutId = "SELECT count(*) as amount from session where id_session = #{idSession} " +
                                        " and status = 1";
    @Select(checkSessionWithoutId)
    @Results(value = {
            @Result(column = "amount")
    })
    Integer checkSessionWithoutId(@Param("idSession") String idSession);

    final String destroySessionWithoutId = "UPDATE session set status = 0 where id_session = #{idSession} ";
    @Update(destroySessionWithoutId)
    void destroySessionWithoutId(@Param("idSession") String idSession);

    final String checkSessionExpiredWithoutSession = "SELECT count(*) as amount from session where " +
            "id_user = #{idUser} and valid_date < now() and status = 1";
    @Select(checkSessionExpiredWithoutSession)
    @Results(value = {
            @Result(column = "amount")
    })
    Integer checkSessionExpiredWithoutSession(@Param("idUser") String idUser);

    final String getIdUserSession = "SELECT id_user from session where " +
            "id_session = #{idSession} and status = 1";
    @Select(getIdUserSession)
    @Results(value = {
            @Result(column = "id_user")
    })
    String getIdUserSession(@Param("idSession") String idSession);

}
