package dana.order.usecase.validation;

import dana.order.usecase.validate.ValidatePayAVoucher;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestValidatePayAVoucher {
    @Autowired
    ValidatePayAVoucher validatePayAVoucher;

    @Test
    public void contextLoads() throws Exception {
        assertThat(validatePayAVoucher).isNotNull();
    }

    @Test
    void validateIDTransactionCheck(){
        assertEquals(true, validatePayAVoucher.idTransactionCheck("12"));
        assertEquals(false, validatePayAVoucher.idTransactionCheck("-23"));
        assertEquals(false, validatePayAVoucher.idTransactionCheck("dr"));
        assertEquals(false, validatePayAVoucher.idTransactionCheck("23gd"));
    }

    @Test
    void validatePayAVoucherCheck(){
        JSONObject json = reproducePayInput();
        assertEquals("083", validatePayAVoucher.check(json).getValue());

        json = reproducePayInput();
        json.remove("idTransaction");
        assertEquals("002", validatePayAVoucher.check(json).getValue());
    }

    private JSONObject reproducePayInput(){
        JSONObject json = new JSONObject();
        json.put("idTransaction", "12");
        return json;
    }
}
