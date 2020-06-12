package com.MemberDomain.usecase.port;

import com.MemberDomain.model.request.RegisterRequest;
import com.MemberDomain.model.response.RegisterLoginResponse;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface Balance_mapper {

    final String registerBalance = "INSERT INTO tbl_balances (idUser, balance)\n" +
            "VALUES (#{idUser}, '0');";

    @Insert(registerBalance)
    void registerBalance(String idUser);

}
