package dana.order.usecase.broadcast;

import dana.order.entity.Transaction;
import dana.order.entity.Voucher;
import dana.order.usecase.broadcast.model.NewVoucher;
import dana.order.usecase.port.DatabaseMapper;
import dana.order.usecase.port.DatabaseRepository;
import dana.order.usecase.port.TransactionRepository;
import dana.order.usecase.port.VoucherRepository;
import org.json.simple.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class VoucherListener {

    @Autowired
    DatabaseRepository databaseRepository;

    @Autowired
    VoucherRepository voucherRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @RabbitListener(queues = "${spring.rabbitmq.queue.listener}",containerFactory = "createListener")
    public synchronized void recieveMessage(NewVoucher vouchers) {

        System.out.println("VOUCHER RAW : " +vouchers.toJsonString());

        Voucher voucher = new Voucher();
        voucher.setIdVoucher(Integer.valueOf(""+vouchers.getIdVoucher()));
        voucher.setIdMerchant(Integer.valueOf(""+vouchers.getIdMerchant()));
        voucher.setVoucherName(""+vouchers.getVoucherName());
        voucher.setVoucherPrice(vouchers.getVoucherPrice());
        voucher.setMaxDiscountPrice(vouchers.getMaxDiscount());
        voucher.setDiscount(vouchers.getDiscount());
        voucher.setVoucherQuantity(vouchers.getQuota());
        voucher.setIsActive(vouchers.getStatus());
        voucher.setExpiredDate(vouchers.getExpiredDate());

        if (voucherRepository.isVoucherExists(voucher.getIdVoucher()) == Boolean.FALSE){
            databaseRepository.createNewVoucher(voucher);
        }else {
            databaseRepository.updateAVoucher(voucher);
        }

        System.out.println("VOUCHER RESULT : "+vouchers.getIdVoucher());
    }

}
