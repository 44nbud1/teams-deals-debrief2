package dana.order.usecase.validate;

import dana.order.entity.DealsStatus;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class ValidateTOPUP {
    public DealsStatus check(JSONObject json){
        if (json.get("virtualNumber") == null){
            return DealsStatus.FILL_ALL_FORMS;
        }

        if (json.get("amount") == null){
            return DealsStatus.FILL_ALL_FORMS;
        }

        if (virtualNumberCheck(""+json.get("virtualNumber")) == Boolean.FALSE){
            return DealsStatus.VIRTUAL_ACCOUNT_INVALID;
        }

        if (amountCheck(""+json.get("amount")) == Boolean.FALSE){
            return DealsStatus.AMOUNT_INVALID;
        }
        return DealsStatus.OK;
    }

    public Boolean virtualNumberCheck(String va){
        String regex = "^[\\d]{14,16}$";
        if(!Pattern.matches(regex, va)){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Boolean amountCheck(String amount){
        String regex = "^(?=.*[\\d])(?!.*[\\D]).+$|^[\\d]+[.]{1}[\\d]+$";
        if(!Pattern.matches(regex, amount)){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
