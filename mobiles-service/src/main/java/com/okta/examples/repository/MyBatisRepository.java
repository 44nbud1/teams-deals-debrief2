package com.okta.examples.repository;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface MyBatisRepository {

//    final String login = "select * from users where email = #{email} and password = #{password}";
//    @Select(login)
//    @Results(value = {
//            @Result(property = "idUser", column = "id_user"),
//            @Result(property = "email", column = "email"),
//            @Result(property = "password", column = "password"),
//            @Result(property = "telephone", column = "telephone")
//    })
//    User login(@Param("email") String email, @Param("password") String password);
//
//    final String findByTelephone = "select * from users where telephone = #{telephone}";
//    @Select(findByTelephone)
//    @Results(value = {
//            @Result(property = "idUser", column = "id_user"),
//            @Result(property = "email", column = "email"),
//            @Result(property = "first_name", column = "first_name"),
//            @Result(property = "last_name", column = "last_name"),
//            @Result(property = "password", column = "password"),
//            @Result(property = "is_active", column = "is_active")
//    })
//    User findByTelephone(@Param("telephone") String telephone);
//
//    final String register = "INSERT INTO users (id_user, email, telephone, first_name, last_name, password)" +
//                            " VALUES( #{idUser}, #{email}, #{phoneNumber}, #{first_name}, #{last_name}, #{password}) ";
//    @Insert(register)
//    void register(RegisterRequest registerRequest);
//
//    final String checkEmailandTelephone = " SELECT count(*) from users where email = #{email} or telephone = #{telephone}";
//    @Select(checkEmailandTelephone)
//    int checkEmailandTelephone(@Param("email") String email, @Param("telephone") String telephone);
//
//    final String setActiveId = "UPDATE users set is_active = 1 where id_user = #{idUser}";
//    @Update(setActiveId)
//    void setActiveId(@Param("idUser") String id);
//
//    final String setActiveTelephone = "UPDATE users set is_active = 1 where telephone = #{telephone}";
//    @Update(setActiveTelephone)
//    void setActiveTelephone(@Param("telephone") String telephone);
//
//    final String saveToken = "INSERT INTO tokens (id_user, token, created_at, updated_at) VALUES" +
//                            " (#{id}, #{token}, NOW(), NOW())";
//    @Insert(saveToken)
//    void saveToken (@Param("id") String id, @Param("token") String token);
//
//    final String updateToken = "UPDATE tokens set token = #{token} ,updated_at = NOW() where id_user = #{id}";
//    @Update(updateToken)
//    void updateToken(@Param("id") String id, @Param("token") String token);
//
//    final String checkToken = "SELECT token from tokens where id_user = #{id} ORDER BY created_at DESC LIMIT 1";
//    @Select(checkToken)
//    @Results(value = {
//        @Result(property = "token", column = "token")
//    })
//    String checkToken (@Param("id") String id);
//
//    final String setTokenInvalid = "UPDATE tokens set is_valid = 0 where id_user = #{id}";
//    @Update(setTokenInvalid)
//    void setTokenInvalid (@Param("id") String id);
//
//    final String logoutToken = "UPDATE tokens set is_valid = 0 where id = {id} and token = #{token}";
//    @Update(logoutToken)
//    void logoutToken (@Param("id") String id, @Param("token") String token);


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

    final String checkSessionExpiredWithoutId = "SELECT count(*) as amount from session where " +
            "id_session = #{idSession} and valid_date > now()";
    @Select(checkSessionExpiredWithoutId)
    @Results(value = {
            @Result(column = "amount")
    })
    Integer checkSessionExpiredWithoutId(@Param("idSession") String idSession);

    final String getIdUserSession = "SELECT id_user from session where " +
            "id_session = #{idSession} and status = 1";
    @Select(getIdUserSession)
    @Results(value = {
            @Result(column = "id_user")
    })
    String getIdUserSession(@Param("idSession") String idSession);
}
