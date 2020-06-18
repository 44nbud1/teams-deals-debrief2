package dana.order.adapter.repository;

import dana.order.entity.ThirdParty;
import dana.order.entity.Transaction;
import dana.order.entity.User;
import dana.order.entity.Voucher;
import dana.order.usecase.port.DatabaseMapper;
import dana.order.usecase.port.DatabaseRepository;
import dana.order.usecase.port.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionRepositoryImpl implements TransactionRepository {

    @Autowired
    DatabaseRepository databaseRepository;

    public Boolean validateBalanceConsistency(String idUser){

        Transaction transaction = databaseRepository.getLatestUserSuccessfulTransaction(idUser);
        User user = databaseRepository.getUserById(idUser);

        Boolean consistency = Boolean.FALSE;
        Integer counter = 0;

        while (consistency == Boolean.FALSE && counter < 3){
            if (transaction != null && user.getUpdatedAt().compareTo(transaction.getUpdatedAt()) >= 0){
                consistency = Boolean.TRUE;
            }else if(transaction == null){
                consistency = Boolean.TRUE;
            }else {
                consistency = Boolean.FALSE;
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){e.printStackTrace();}
            }
            counter += 1;
        }

        return consistency;
    }

    public Boolean validateVoucherConsistency(Integer idVoucher){

        Transaction transaction = databaseRepository.getLatestVoucherSuccessfulTransaction(idVoucher);
        Voucher voucher = databaseRepository.getVoucherById(idVoucher);

        Boolean consistency = Boolean.FALSE;
        Integer counter = 0;

        while (consistency == Boolean.FALSE && counter < 3){
            if (transaction != null && voucher.getUpdatedAt().compareTo(transaction.getUpdatedAt()) >= 0){
                consistency = Boolean.TRUE;
            }else if(transaction == null){
                consistency = Boolean.TRUE;
            }else{
                consistency = Boolean.FALSE;
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){e.printStackTrace();}
            }
            counter += 1;
        }

        return consistency;
    }

    public Boolean checkATransactionExpiration(Integer idTransaction){

        if (databaseRepository.checkATransactionExpiration(idTransaction) > 0){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public void setFinishATransaction(Integer idTransaction){
        databaseRepository.setFinishATransaction(idTransaction);
    }

    public Boolean checkTOPUPThirdParty(String partyCode){
        if (databaseRepository.checkThirdPartyExists(partyCode) == 0){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public void TOPUPBalance(String idUser, Double amount, String virtualNumber, String partyCode){
        databaseRepository.TOPUP(idUser, amount);
        Transaction transaction = databaseRepository.getLatestUserSuccessfulTransaction(idUser);
        ThirdParty thirdParty = databaseRepository.selectThirdPartyByCode(partyCode);
        databaseRepository.addVirtualPayment(transaction.getIdTransaction(), virtualNumber, thirdParty.getIdThirdParty());
    }

    public String getPartyCode(String virtualNumber){
        return virtualNumber.substring(0,4);
    }

    public String getPhoneNumberFromVA(String virtualNumber){
        return (""+virtualNumber).substring(4);
    }

    public Transaction setRefund(String idUser, Double amount, Integer idGoods){
        databaseRepository.makeARefund(idUser, amount, idGoods);
        return databaseRepository.getLatestUserSuccessfulTransaction(idUser);
    }
}
