package com.danapprentech.debrief2.voucherservice.rabbit.consumer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member
{
    private String idUser;
    private String name;
    private Date createdAt;
    private Date updatedAt;
}
