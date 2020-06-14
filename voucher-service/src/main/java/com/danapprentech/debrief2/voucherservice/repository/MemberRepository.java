package com.danapprentech.debrief2.voucherservice.repository;

import com.danapprentech.debrief2.voucherservice.rabbit.model.MemberConsumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberConsumer,Long>
{

}
