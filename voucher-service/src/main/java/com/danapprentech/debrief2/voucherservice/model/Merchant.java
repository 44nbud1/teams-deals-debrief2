package com.danapprentech.debrief2.voucherservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "merchant")

//@OptimisticLocking(type = OptimisticLockType.DIRTY)
//@DynamicUpdate
public class Merchant implements Serializable
{
    @Id
    @Column(name = "id_merchant")
    private Long idMerchant;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;
    @Column(name = "merchant_name")
    private String merchantName;

    @OneToMany(mappedBy = "merchant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("vouchers")
    private List<Voucher> vouchers;

    // mapping to relational database
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_merchant_category1", nullable = false) // --------9
    @JsonIgnore
    @NotNull
    private MerchantCategory merchantCategory;
}
