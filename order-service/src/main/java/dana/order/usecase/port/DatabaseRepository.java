package dana.order.usecase.port;

import dana.order.entity.*;

import java.util.List;

public interface DatabaseRepository {

    Integer getMaxTransactionID();

    void updateAVoucherWithDelta(Voucher voucher);

    void makeARefund(Integer idTransaction, String idUser, Double amount, Integer idGoods);

    void fallingAllExpiredTransaction();

    void createNewUser(User user);

    void updateAUser(User user);

    void createNewVoucher(Voucher voucher);

    void updateAVoucher(Voucher voucher);

    Integer checkVoucherExists(Integer idVoucher);

    Merchant getMerchantById(Integer idMerchant);

    ThirdParty getThirdParty(Integer idTransaction);

    PaymentMethod getPaymentMethod(Integer idPaymentMethod);

    TransactionStatus getTransactionStatus(Integer idTransactionStatus);

    Services getServiceById(Integer idService);

    Integer getTotalTransactionHistory(String idUser,
                                              Integer category1,
                                              Integer category2,
                                              Integer category3,
                                              Integer category4,
                                              String startDate,
                                              String endDate);

    Transaction getUserFirstTransaction(String idUser);

    Transaction getUserLastTransaction(String idUser);

    List<TransactionHistoryModel> selectTransactionHistory(String idUser,
                                                                  Integer category1,
                                                                  Integer category2,
                                                                  Integer category3,
                                                                  Integer category4,
                                                                  String startDate,
                                                                  String endDate,
                                                                  Integer page);

    ThirdParty selectThirdPartyByCode(String partyCode);

    void addVirtualPayment(Integer idTransaction, String virtualNumber, Integer idThirdParty);

    void TOPUP(Integer idTransaction, String idUser, Double amount);

    Integer checkThirdPartyExists(String partyCode);

    void setFinishATransaction(Integer idTransaction);

    Integer checkATransactionExpiration(Integer idTransaction);

    Transaction getTransactionById(Integer idTransaction);

    void fallingATransaction(String idUser, Integer idTransaction);

    User getUserById(String idUser);

    Transaction getLatestUserSuccessfulTransaction(String idUser);

    Transaction getLatestUserInProgressTransaction(String idUser);

    Transaction getLatestVoucherSuccessfulTransaction(Integer idVoucher);

    Integer getValidVoucherById(Integer idVoucher);

    Voucher getVoucherById(Integer idVoucher);

    Integer getUserVoucherInUse(String idUser, Integer idVoucher);

    void insertNewOrder(Integer idTransaction, String idUser, Double amount, Integer idVoucher);

    Integer getUserExistById(String idUser);
}
