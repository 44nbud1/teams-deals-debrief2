package com.danapprentech.debrief2.voucherservice.repository;

import com.danapprentech.debrief2.voucherservice.model.Merchant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long>
{

//    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Merchant findByIdMerchant(Long id);

    Merchant findByMerchantNameStartsWithIgnoreCase(String name);
    Page<Merchant> findByMerchantNameStartsWithIgnoreCase(String name, Pageable pageable);
    Merchant findByMerchantNameContaining(String name);
}
