package dana.order.usecase.port;

import dana.order.entity.Transaction;

public interface TransactionRepository {
    Boolean validateBalanceConsistency(String idUser);
    Boolean validateVoucherConsistency(Integer idVoucher);
    Boolean checkATransactionExpiration(Integer idTransaction);
    void setFinishATransaction(Integer idTransaction);
    String getPartyCode(String virtualNumber);
    Boolean checkTOPUPThirdParty(String partyCode);
    Integer TOPUPBalance(String idUser, Double amount, String virtualNumber, String partyCode);
    String getPhoneNumberFromVA(String virtualNumber);
    Transaction setRefund(String idUser, Double amount, Integer idGoods);
    Transaction getTransactionById(Integer idTransaction);
    void fallingATransaction(String idUser, Integer idTransaction);
    void fallingAllExpiredTransaction();
}
