package com.MemberDomain.usecase.port;

import com.MemberDomain.model.response.OtpResponse;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface OtpMapper {

    final String createOTP = "INSERT INTO tbl_otps (otp, expiredDate, idUser)\n" +
            "VALUES ('0000', NOW() + INTERVAL 1 MINUTE, #{idUser});";

    final String checkOTP = "SELECT * FROM tbl_otps WHERE idUser =  #{idUser};";

    final String updateOTP = "UPDATE tbl_otps SET expiredDate = NOW() + INTERVAL 1 MINUTE WHERE idUser = #{idUser}";

    final String matchOTP = "SELECT * FROM tbl_otps WHERE idUser =  #{idUser} AND otp = #{otp};";

    @Insert(createOTP)
    void createOTP(String idUser);

    @Select(checkOTP)
    OtpResponse checkOTP(String idUser);

    @Update(updateOTP)
    void updateOTP(String idUser);

    @Select(matchOTP)
    OtpResponse matchOTP(String idUser, String otp);
}