package dana.order.usecase.validation;

import dana.order.usecase.validate.ValidateBuyAVoucher;
import dana.order.usecase.validate.ValidateDetailedTransactionHistory;
import dana.order.usecase.validate.ValidatePayAVoucher;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class TestValidateDetailedTransactionHistory {
    @Autowired
    ValidateDetailedTransactionHistory validateDetailedTransactionHistory;

    @Test
    public void contextLoads() throws Exception {
        assertThat(validateDetailedTransactionHistory).isNotNull();
    }

    @Test
    void validateIDTransactionCheck(){
        assertEquals(true, validateDetailedTransactionHistory.idTransactionCheck("12"));
        assertEquals(false, validateDetailedTransactionHistory.idTransactionCheck("-23"));
        assertEquals(false, validateDetailedTransactionHistory.idTransactionCheck("dr"));
        assertEquals(false, validateDetailedTransactionHistory.idTransactionCheck("23gd"));
    }

    @Test
    void validatePayAVoucherCheck(){
        JSONObject json = reproducePayInput();
        assertEquals("083", validateDetailedTransactionHistory.check(json).getValue());

        json = reproducePayInput();
        json.remove("idTransaction");
        assertEquals("002", validateDetailedTransactionHistory.check(json).getValue());
    }

    private JSONObject reproducePayInput(){
        JSONObject json = new JSONObject();
        json.put("idTransaction", "12");
        return json;
    }
}
