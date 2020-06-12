package dana.order.usecase.broadcast;

import dana.order.entity.Voucher;
import dana.order.usecase.port.DatabaseMapper;
import dana.order.usecase.port.VoucherRepository;
import org.json.simple.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class VoucherListener {

    @Autowired
    DatabaseMapper databaseMapper;

    @Autowired
    VoucherRepository voucherRepository;

    @RabbitListener(queues = "${spring.rabbitmq.queue.listener}",containerFactory = "createListener")
    public void recieveMessage(JSONObject json) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date expiredDate = new Date();

        try{
            expiredDate = sdf.parse(""+json.get("expiredDate"));
        }catch (ParseException e){e.printStackTrace();}

        Voucher voucher = new Voucher();
        voucher.setIdVoucher(Integer.valueOf(""+json.get("id")));
        voucher.setIdMerchant(Integer.valueOf(""+json.get("idMerchant")));
        voucher.setVoucherName(""+json.get("voucherName"));
        voucher.setVoucherPrice(Double.valueOf(""+json.get("voucherPrice")));
        voucher.setMaxDiscountPrice(Double.valueOf(""+json.get("maxDiscount")));
        voucher.setDiscount(Double.valueOf(""+json.get("discount")));
        voucher.setVoucherQuantity(Integer.valueOf(""+json.get("quota")));
        voucher.setIsActive(Boolean.valueOf(""+json.get("status")));
        voucher.setExpiredDate(expiredDate);

        if (voucherRepository.isVoucherExists(voucher.getIdVoucher()) == Boolean.FALSE){
            databaseMapper.createNewVoucher(voucher);
        }else {
            databaseMapper.updateAVoucher(voucher);
        }

        System.out.println(json);
    }

}
