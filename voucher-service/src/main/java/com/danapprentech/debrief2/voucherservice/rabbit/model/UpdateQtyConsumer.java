package com.danapprentech.debrief2.voucherservice.rabbit.model;//package com.danapprentech.debrief2.voucherservice.rabbit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor

//@OptimisticLocking(type = OptimisticLockType.DIRTY)
//@DynamicUpdate
public class UpdateQtyConsumer implements Serializable
{
    private Long idVoucher;
    private Integer qty;
}
