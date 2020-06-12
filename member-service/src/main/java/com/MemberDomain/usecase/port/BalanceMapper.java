package com.MemberDomain.usecase.port;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface BalanceMapper {

    final String registerBalance = "INSERT INTO tbl_balances (idUser, balance)\n" +
            "VALUES (#{idUser}, '0');";

    @Insert(registerBalance)
    void registerBalance(String idUser);

}
