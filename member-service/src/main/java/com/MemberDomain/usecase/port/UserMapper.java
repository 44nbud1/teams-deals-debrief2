package com.MemberDomain.usecase.port;

import com.MemberDomain.model.request.RegisterRequest;
import com.MemberDomain.model.response.ProfileResponse;
import com.MemberDomain.model.response.RegisterLoginResponse;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {

    final String registerUser = "INSERT INTO tbl_users (idUser, name, email, phoneNumber, password, idRole)\n" +
            "VALUES (#{idUser}, #{name}, #{email}, #{phoneNumber}, #{password}, '2');";

    final String emailCheck = "SELECT * FROM tbl_users WHERE email = #{email}";
    final String phoneCheck = "SELECT * FROM tbl_users WHERE phoneNumber = #{phoneNumber}";

    final String getAll = "SELECT tu.idUser, tu.name, tu.email, tu.phoneNumber, tb.balance, tu.idRole, tr.roleName\n" +
            "FROM tbl_users AS tu, tbl_balances AS tb, tbl_roles AS tr\n" +
            "WHERE tu.idUser = tb.idUser AND tu.idRole = tr.idRole\n" +
            "ORDER BY tu.created_at ASC";

    final String login = "SELECT * from tbl_users WHERE phoneNumber = #{phoneNumber} AND password = #{password}";

    final String getUserData = "SELECT tu.idUser, tu.name, tu.email, tu.phoneNumber, tb.balance, tu.idRole, tr.roleName\n" +
            "FROM tbl_users AS tu, tbl_balances AS tb, tbl_roles AS tr\n" +
            "WHERE tu.idUser = tb.idUser AND tu.idRole = tr.idRole\n" +
            "AND tu.idUser = #{idUser}";

    final String getUserProfile = "SELECT * FROM tbl_users WHERE idUser = #{idUser}";

    final String editProfile = "";
    final String changePassword = "UPDATE tbl_users SET password = #{password} WHERE idUser = #{idUser}";
    final String editProfilePro = "";

    @Insert(registerUser)
    void registerUser(RegisterRequest register);

    @Select(emailCheck)
    RegisterLoginResponse emailCheck(String email);

    @Select(phoneCheck)
    RegisterLoginResponse phoneCheck(String phoneNumber);

    @Select(login)
    RegisterLoginResponse login(String phoneNumber, String password);

    @Select(getUserData)
    @Results(value = {
            @Result(property = "idUser", column = "idUser"),
            @Result(property = "name", column = "name"),
            @Result(property = "email", column = "email"),
            @Result(property = "phoneNumber", column = "phoneNumber"),
            @Result(property = "balance", column = "balance"),
            @Result(property = "idRole", column = "idRole"),
            @Result(property = "roleName", column = "roleName")
    })
    RegisterLoginResponse getUserData(String idUser);

    @Select(getUserProfile)
    @Results(value = {
            @Result(property = "idUser", column = "idUser"),
            @Result(property = "name", column = "name"),
            @Result(property = "email", column = "email"),
            @Result(property = "phoneNumber", column = "phoneNumber")
    })
    ProfileResponse getUserProfile(String idUser);

    @Select(phoneCheck)
    ProfileResponse phoneOTPCheck(String phoneNumber);

    @Update(changePassword)
    void changePassword(String password, String idUser);

    @Select(getAll)
    @Results(value = {
            @Result(property = "idUser", column = "idUser"),
            @Result(property = "name", column = "name"),
            @Result(property = "email", column = "name"),
            @Result(property = "phoneNumber", column = "phoneNumber"),
            @Result(property = "idRole", column = "idRole")
    })
    List<RegisterLoginResponse> getAll();
}
