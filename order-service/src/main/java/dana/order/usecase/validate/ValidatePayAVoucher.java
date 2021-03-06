package dana.order.usecase.validate;

import dana.order.entity.DealsStatus;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class ValidatePayAVoucher {
    public DealsStatus check(JSONObject json){
        if(json.get("idTransaction") == null){
            return DealsStatus.FILL_ALL_FORMS;
        }

        if(idTransactionCheck(""+json.get("idTransaction")) == Boolean.FALSE){
            return DealsStatus.DATA_INVALID;
        }

        return DealsStatus.OK;
    }

    public Boolean idTransactionCheck(String idTransaction){
        String regex = "^[\\d]+$";
        if(!Pattern.matches(regex, idTransaction)){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
