package com.danapprentech.debrief2.voucherservice.service;

import com.danapprentech.debrief2.voucherservice.model.Voucher;
import com.danapprentech.debrief2.voucherservice.repository.VoucherRepository;
import org.hibernate.exception.LockAcquisitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Optional;

//@Transactional(isolation = Isolation.SERIALIZABLE)
@Transactional
@Service
public class VoucherServiceImpl implements VoucherService
{
    @Autowired
    VoucherRepository voucherRepository;

    @Override
    public Page<Voucher> findByMerchantCategoryContaining(String merchantCategory, Pageable pageable)
    {
        return null;
    }

    @Override
    public Page<Voucher> findByIdVoucher(Long id, Pageable pageable)
    {
        return null;
    }

    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "3000")})
    public Voucher findByIdVoucher(Long id)
    {
        return voucherRepository.findByIdVoucher(id);
    }

    @Override
    public Voucher save(Voucher voucher)
    {
        return voucherRepository.save(voucher);
    }

//    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "3000")})
    @Override
    public Voucher updateVoucher(Voucher voucher)
    {
        return voucherRepository.save(voucher);
    }

    @Override
    public Optional<Voucher> findById(Long id)
    {
        return voucherRepository.findById(id);
    }

    @Override
    public Page<Voucher> findAll()
    {
        return (Page<Voucher>) voucherRepository.findAll();
    }

    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "3000")})
    public Voucher findByVoucherName(String voucherName)
    {
        return voucherRepository.findByVoucherName(voucherName);
    }
}
