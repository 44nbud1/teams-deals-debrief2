package com.MemberDomain.usecase.port;

import com.MemberDomain.model.response.OTPResponse;
import com.MemberDomain.model.response.ProfileResponse;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface OTP_mapper {

    final String createOTP = "INSERT INTO tbl_otps (otp, date, is_active, idUser)\n" +
            "VALUES ('0000', NOW() + INTERVAL 1 MINUTE, '1', #{idUser});";

    final String checkOTP = "SELECT * FROM tbl_otps WHERE idUser =  #{idUser};";

    final String updateOTP = "UPDATE tbl_otps SET date = NOW() + INTERVAL 1 MINUTE WHERE idUser = #{idUser}";

    final String matchOTP = "SELECT * FROM tbl_otps WHERE idUser =  #{idUser} AND otp = #{otp};";

    @Insert(createOTP)
    void createOTP(String idUser);

    @Select(checkOTP)
    OTPResponse checkOTP(String idUser);

    @Update(updateOTP)
    void updateOTP(String idUser);

    @Select(matchOTP)
    OTPResponse matchOTP(String idUser, String otp);
}
