package com.MemberDomain.usecase.port;

import com.MemberDomain.model.request.RegisterRequest;
import com.MemberDomain.model.response.LoginResponse;
import com.MemberDomain.model.response.PasswordResponse;
import com.MemberDomain.model.response.ProfileResponse;
import com.MemberDomain.model.response.UserDataResponse;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {

    final String registerUser = "INSERT INTO tbl_users (name, email, phoneNumber, password, idRole)\n" +
            "VALUES (#{name}, #{email}, #{phoneNumber}, #{password}, '2');";

    final String emailCheck = "SELECT * FROM tbl_users WHERE email = #{email}";
    final String phoneCheck = "SELECT * FROM tbl_users WHERE phoneNumber = #{phoneNumber}";

    final String getAll = "SELECT tu.idUser, tu.name, tu.email, tu.phoneNumber, tb.balance, tu.idRole, tr.roleName\n" +
            "FROM tbl_users AS tu, tbl_balances AS tb, tbl_roles AS tr\n" +
            "WHERE tu.idUser = tb.idUser AND tu.idRole = tr.idRole\n" +
            "ORDER BY tu.created_at ASC";

    final String getUserLoginData = "SELECT * from tbl_users WHERE phoneNumber = #{phoneNumber}";

    final String getUserData = "SELECT tu.idUser, tu.name, tu.email, tu.phoneNumber, tb.balance, tu.idRole, tr.roleName\n" +
            "FROM tbl_users AS tu, tbl_balances AS tb, tbl_roles AS tr\n" +
            "WHERE tu.idUser = tb.idUser AND tu.idRole = tr.idRole\n" +
            "AND tu.idUser = #{idUser}";

    final String getUserProfile = "SELECT * FROM tbl_users WHERE idUser = #{idUser}";

    final String getUserPassword = "SELECT password FROM tbl_users WHERE idUser = #{idUser}";

    final String changePassword = "UPDATE tbl_users SET password = #{password} WHERE idUser = #{idUser}";
    final String changeName = "UPDATE tbl_users SET name = #{name} WHERE idUser = #{idUser}";
    final String changeEmail = "UPDATE tbl_users SET email = #{email} WHERE idUser = #{idUser}";

    @Insert(registerUser)
    @Options(useGeneratedKeys = true, keyProperty = "idUser")
    void registerUser(RegisterRequest register);

    @Select(emailCheck)
    UserDataResponse emailCheck(String email);

    @Select(phoneCheck)
    UserDataResponse phoneCheck(String phoneNumber);

    @Select(getUserLoginData)
    LoginResponse getUserLoginData(String phoneNumber);

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
    UserDataResponse getUserData(String idUser);

    @Select(getUserProfile)
    @Results(value = {
            @Result(property = "idUser", column = "idUser"),
            @Result(property = "name", column = "name"),
            @Result(property = "email", column = "email"),
            @Result(property = "phoneNumber", column = "phoneNumber")
    })
    ProfileResponse getUserProfile(String idUser);

    @Select(getUserPassword)
    PasswordResponse getUserPassword(String idUser);

    @Select(phoneCheck)
    ProfileResponse phoneOTPCheck(String phoneNumber);

    @Update(changePassword)
    void changePassword(String idUser, String password);

    @Update(changeName)
    void changeName(String idUser, String name);

    @Update(changeEmail)
    void changeEmail(String idUser, String email);

    @Select(getAll)
    @Results(value = {
            @Result(property = "idUser", column = "idUser"),
            @Result(property = "name", column = "name"),
            @Result(property = "email", column = "name"),
            @Result(property = "phoneNumber", column = "phoneNumber"),
            @Result(property = "idRole", column = "idRole")
    })
    List<UserDataResponse> getAll();
}
