package dana.order.adapter.repository;

import dana.order.entity.*;
import dana.order.usecase.port.DatabaseMapper;
import dana.order.usecase.port.DatabaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseRepositoryImpl implements DatabaseRepository {

    @Autowired
    DatabaseMapper databaseMapper;

    public Integer getMaxTransactionID(){
        return databaseMapper.getMaxTransactionID();
    }

    public void updateAVoucherWithDelta(Voucher voucher){
        //UPDATE
        Boolean retry = Boolean.TRUE;
        while (retry == Boolean.TRUE){
            try {
                databaseMapper.updateAVoucherWithDelta(voucher);
                retry = Boolean.FALSE;
            }catch (DeadlockLoserDataAccessException e){
                System.out.println("A Deadlock in updating a voucher with delta.");
                e.printStackTrace();
                retry = Boolean.TRUE;
            }
        }
    }

    public void makeARefund(Integer idTransaction, String idUser, Double amount, Integer idGoods){
        //INSERT
        databaseMapper.makeARefund(idTransaction, idUser, amount, idGoods);
    }

    public void fallingAllExpiredTransaction(){
        //UPDATE
        Boolean retry = Boolean.TRUE;
        while (retry == Boolean.TRUE){
            try {
                databaseMapper.fallingAllExpiredTransaction();
                retry = Boolean.FALSE;
            }catch (DeadlockLoserDataAccessException e){
                System.out.println("A Deadlock in expiring all expired transaction.");
                e.printStackTrace();
                retry = Boolean.TRUE;
            }
        }
    }

    public void createNewUser(User user){
        //INSERT
        databaseMapper.createNewUser(user);
    }

    public void updateAUser(User user){
        //UPDATE
        Boolean retry = Boolean.TRUE;
        while (retry == Boolean.TRUE){
            try {
                databaseMapper.updateAUser(user);
                retry = Boolean.FALSE;
            }catch (DeadlockLoserDataAccessException e){
                System.out.println("A Deadlock in updating a user.");
                e.printStackTrace();
                retry = Boolean.TRUE;
            }
        }
    }

    public void createNewVoucher(Voucher voucher){
        databaseMapper.createNewVoucher(voucher);
    }

    public void updateAVoucher(Voucher voucher){
        Boolean retry = Boolean.TRUE;
        while (retry == Boolean.TRUE){
            try {
                databaseMapper.updateAVoucher(voucher);
                retry = Boolean.FALSE;
            }catch (DeadlockLoserDataAccessException e){
                System.out.println("A Deadlock in updating a voucher.");
                e.printStackTrace();
                retry = Boolean.TRUE;
            }
        }
    }

    public Integer checkVoucherExists(Integer idVoucher){
        //SELECT
        return databaseMapper.checkVoucherExists(idVoucher);
    }

    public Merchant getMerchantById(Integer idMerchant){
        //SELECT
        return databaseMapper.getMerchantById(idMerchant);
    }

    public ThirdParty getThirdParty(Integer idTransaction){
        //SELECT
        return databaseMapper.getThirdParty(idTransaction);
    }

    public PaymentMethod getPaymentMethod(Integer idPaymentMethod){
        //SELECT
        return databaseMapper.getPaymentMethod(idPaymentMethod);
    }

    public TransactionStatus getTransactionStatus(Integer idTransactionStatus){
        //SELECT
        return databaseMapper.getTransactionStatus(idTransactionStatus);
    }

    public Services getServiceById(Integer idService){
        //SELECT
        return databaseMapper.getServiceById(idService);
    }

    public Integer getTotalTransactionHistory(String idUser,
                                              Integer category1,
                                              Integer category2,
                                              Integer category3,
                                              Integer category4,
                                              String startDate,
                                              String endDate){
        //SELECT
        return databaseMapper.getTotalTransactionHistory(idUser, category1, category2,
                category3, category4, startDate, endDate);
    }

    public Transaction getUserFirstTransaction(String idUser){
        //SELECT
        return databaseMapper.getUserFirstTransaction(idUser);
    }

    public Transaction getUserLastTransaction(String idUser){
        //SELECT
        return databaseMapper.getUserLastTransaction(idUser);
    }

    public List<TransactionHistoryModel> selectTransactionHistory(String idUser,
                                                                  Integer category1,
                                                                  Integer category2,
                                                                  Integer category3,
                                                                  Integer category4,
                                                                  String startDate,
                                                                  String endDate,
                                                                  Integer page){
        //SELECT
        return databaseMapper.selectTransactionHistory(idUser, category1, category2, category3,
                category4, startDate, endDate, page);
    }

    public ThirdParty selectThirdPartyByCode(String partyCode){
        //SELECT
        return databaseMapper.selectThirdPartyByCode(partyCode);
    }

    public void addVirtualPayment(Integer idTransaction, String virtualNumber, Integer idThirdParty){
        //INSERT
        databaseMapper.addVirtualPayment(idTransaction, virtualNumber, idThirdParty);
    }

    public void TOPUP(Integer idTransaction, String idUser, Double amount){
        //INSERT
        databaseMapper.TOPUP(idTransaction, idUser, amount);
    }

    public Integer checkThirdPartyExists(String partyCode){
        //SELECT
        return databaseMapper.checkThirdPartyExists(partyCode);
    }

    public void setFinishATransaction(Integer idTransaction){
        //UPDATE
        Boolean retry = Boolean.TRUE;
        while (retry == Boolean.TRUE){
            try {
                databaseMapper.setFinishATransaction(idTransaction);
                retry = Boolean.FALSE;
            }catch (DeadlockLoserDataAccessException e){
                System.out.println("A Deadlock in updating a transaction.");
                e.printStackTrace();
                retry = Boolean.TRUE;
            }
        }
    }

    public Integer checkATransactionExpiration(Integer idTransaction){
        //SELECT
        return databaseMapper.checkATransactionExpiration(idTransaction);
    }

    public Transaction getTransactionById(Integer idTransaction){
        //SELECT
        return databaseMapper.getTransactionById(idTransaction);
    }

    public void fallingATransaction(String idUser, Integer idTransaction){
        //UPDATE
        Boolean retry = Boolean.TRUE;
        while (retry == Boolean.TRUE){
            try {
                databaseMapper.fallingATransaction(idUser, idTransaction);
                retry = Boolean.FALSE;
            }catch (DeadlockLoserDataAccessException e){
                System.out.println("A Deadlock in expiring a transaction.");
                e.printStackTrace();
                retry = Boolean.TRUE;
            }
        }
    }

    public User getUserById(String idUser){
        //SELECT
        return databaseMapper.getUserById(idUser);
    }

    public Transaction getLatestUserSuccessfulTransaction(String idUser){
        //SELECT
        return databaseMapper.getLatestUserSuccessfulTransaction(idUser);
    }

    public Transaction getLatestUserInProgressTransaction(String idUser){
        //SELECT
        return databaseMapper.getLatestUserInProgressTransaction(idUser);
    }

    public Transaction getLatestVoucherSuccessfulTransaction(Integer idVoucher){
        //SELECT
        return databaseMapper.getLatestVoucherSuccessfulTransaction(idVoucher);
    }

    public Integer getValidVoucherById(Integer idVoucher){
        //SELECT
        return databaseMapper.getValidVoucherById(idVoucher);
    }

    public Voucher getVoucherById(Integer idVoucher){
        //SELECT
        return databaseMapper.getVoucherById(idVoucher);
    }

    public Integer getUserVoucherInUse(String idUser, Integer idVoucher){
        //SELECT
        return databaseMapper.getUserVoucherInUse(idUser, idVoucher);
    }

    public void insertNewOrder(Integer idTransaction, String idUser, Double amount, Integer idVoucher){
        //INSERT
        databaseMapper.insertNewOrder(idTransaction, idUser, amount, idVoucher);
    }

    public Integer getUserExistById(String idUser){
        //SELECT
        return databaseMapper.getUserExistById(idUser);
    }

    public String getUniqueKey(String key){
        return databaseMapper.getUniqueKey(key);
    }

    public void addUniqueKey(String key, String response){
        databaseMapper.addUniqueKey(key, response);
    }
}
