package dana.order.usecase.validate;

import dana.order.entity.DealsStatus;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class ValidateBuyAVoucher {
    public DealsStatus check(JSONObject json){
        if(json.get("idVoucher") == null){
            return DealsStatus.FILL_ALL_FORMS;
        }

        if(idVoucherCheck(""+json.get("idVoucher")) == Boolean.FALSE){
            return DealsStatus.INVALID_VOUCHER_ID;
        }

        return DealsStatus.OK;
    }

    public Boolean idVoucherCheck(String idVoucher){
        String regex = "^[\\d]+$";
        if(!Pattern.matches(regex, idVoucher)){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
