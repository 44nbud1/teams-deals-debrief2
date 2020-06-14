package com.danapprentech.debrief2.voucherservice.rabbit.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "member_voucher_service")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberConsumer
{
    @Id
    @Column(name = "id_user")
    private String idUser;
    private String phoneNumber;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at")
    private Date createdAt;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_at")
    private Date updatedAt;
}
