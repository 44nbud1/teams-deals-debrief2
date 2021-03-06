package dana.order.adapter.repository;

import dana.order.entity.Voucher;
import dana.order.usecase.port.DatabaseRepository;
import dana.order.usecase.port.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoucherRepositoryImpl implements VoucherRepository {

    @Autowired
    DatabaseRepository databaseRepository;

    public Boolean validateExpiration(Integer idVoucher){
        if (databaseRepository.getValidVoucherById(idVoucher) < 1){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Boolean validateQuantity(Integer idVoucher){
        Voucher voucher = databaseRepository.getVoucherById(idVoucher);
        if (voucher.getVoucherQuantity() < 1){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Integer insertNewOrder(String idUser, Integer idVoucher){
        Voucher voucher = databaseRepository.getVoucherById(idVoucher);
        Integer idTransaction = databaseRepository.getMaxTransactionID() + 1;
        databaseRepository.insertNewOrder(idTransaction, idUser, voucher.getVoucherPrice(), idVoucher);
        return idTransaction;
    }

    public Boolean isVoucherExists(Integer idVoucher){
        if (databaseRepository.checkVoucherExists(idVoucher) == 0){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
