package com.MemberDomain.usecase.port;

import com.MemberDomain.model.response.OtpResponse;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface OtpMapper {

    final String createOTP = "INSERT INTO tbl_otps (idUser, otp, expiredDate, matchStatus)\n" +
            "VALUES (#{idUser}, '0000', NOW() + INTERVAL 1 MINUTE, '0');";

    final String checkOTP = "SELECT * FROM tbl_otps WHERE idUser =  #{idUser};";

    final String updateOTP = "UPDATE tbl_otps SET expiredDate = NOW() + INTERVAL 1 MINUTE, matchStatus = '0' WHERE idUser = #{idUser}";

    final String matchOTP = "SELECT * FROM tbl_otps WHERE idUser =  #{idUser} AND otp = #{otp};";

    final String matchOTPDate = "SELECT * FROM tbl_otps WHERE idUser =  #{idUser} AND otp = #{otp} AND expiredDate > NOW();";

    final String matchingOTP = "UPDATE tbl_otps SET matchStatus = '1' WHERE idUser = #{idUser}";

    final String unmatchingOTP = "UPDATE tbl_otps SET matchStatus = '0' WHERE idUser = #{idUser}";

    @Insert(createOTP)
    void createOTP(String idUser);

    @Select(checkOTP)
    OtpResponse checkOTP(String idUser);

    @Update(updateOTP)
    void updateOTP(String idUser);

    @Select(matchOTP)
    OtpResponse matchOTP(String idUser, String otp);

    @Select(matchOTPDate)
    OtpResponse matchOTPDate(String idUser, String otp);

    @Update(matchingOTP)
    void mathcingOTP(String idUser);

    @Update(unmatchingOTP)
    void unmatchingOTP (String idUser);

}
