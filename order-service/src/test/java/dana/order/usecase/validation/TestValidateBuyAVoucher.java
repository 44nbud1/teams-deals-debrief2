package dana.order.usecase.validation;

import dana.order.usecase.validate.ValidateBuyAVoucher;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestValidateBuyAVoucher {
    @Autowired
    ValidateBuyAVoucher validateBuyAVoucher;

    @Test
    public void contextLoads() throws Exception {
        assertThat(validateBuyAVoucher).isNotNull();
    }

    @Test
    void validateIDVoucherCheck(){
        assertEquals(true, validateBuyAVoucher.idVoucherCheck("34"));
        assertEquals(false, validateBuyAVoucher.idVoucherCheck("-12"));
        assertEquals(false, validateBuyAVoucher.idVoucherCheck("erw"));
        assertEquals(false, validateBuyAVoucher.idVoucherCheck("3ge"));
    }

    @Test
    void validateBuyAVoucherCheck(){

        JSONObject json = reproduceVoucherInput();
        json.remove("idVoucher");
        assertEquals("002", validateBuyAVoucher.check(json).getValue());

        json = reproduceVoucherInput();
        json.put("idVoucher", "-22");
        assertEquals("081", validateBuyAVoucher.check(json).getValue());

        json = reproduceVoucherInput();
        assertEquals("083", validateBuyAVoucher.check(json).getValue());
    }

    private JSONObject reproduceVoucherInput(){
        JSONObject json = new JSONObject();
        json.put("idVoucher", "23");
        return json;
    }
}
