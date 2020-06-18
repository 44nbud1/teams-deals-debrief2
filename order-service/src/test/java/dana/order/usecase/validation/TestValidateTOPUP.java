package dana.order.usecase.validation;

import dana.order.usecase.validate.ValidateTOPUP;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestValidateTOPUP {
    @Autowired
    ValidateTOPUP validateTOPUP;

    @Test
    public void contextLoads() throws Exception {
        assertThat(validateTOPUP).isNotNull();
    }

    @Test
    void validateAmountTOPUP(){
        assertEquals(true, validateTOPUP.amountCheck("200000"));
        assertEquals(true, validateTOPUP.amountCheck("10000.00"));
        assertEquals(false, validateTOPUP.amountCheck("-200000"));
        assertEquals(false, validateTOPUP.amountCheck("200f"));
    }

    @Test
    void validateVirtualNumber(){
        assertEquals(true, validateTOPUP.virtualNumberCheck("9030085794422971"));
        assertEquals(true, validateTOPUP.virtualNumberCheck("903008123456789"));
        assertEquals(true, validateTOPUP.virtualNumberCheck("90300812345678"));
        assertEquals(false, validateTOPUP.virtualNumberCheck("90306285794422971"));
        assertEquals(false, validateTOPUP.virtualNumberCheck("903008123"));
    }

    @Test
    void validateTOPUPCheck(){
        JSONObject json = reproduceTOPUPInput();
        assertTrue(validateTOPUP.check(json).getStatus().is2xxSuccessful());
        assertEquals("083", validateTOPUP.check(json).getValue());

        json = reproduceTOPUPInput();
        json.put("virtualNumber", "90300812345");
        assertFalse(validateTOPUP.check(json).getStatus().is2xxSuccessful());
        assertEquals("067", validateTOPUP.check(json).getValue());

        json = reproduceTOPUPInput();
        json.remove("virtualNumber");
        assertFalse(validateTOPUP.check(json).getStatus().is2xxSuccessful());
        assertEquals("002", validateTOPUP.check(json).getValue());

        json = reproduceTOPUPInput();
        json.remove("amount");
        assertFalse(validateTOPUP.check(json).getStatus().is2xxSuccessful());
        assertEquals("002", validateTOPUP.check(json).getValue());

        json = reproduceTOPUPInput();
        json.put("amount", -120003.234);
        assertFalse(validateTOPUP.check(json).getStatus().is2xxSuccessful());
        assertEquals("066", validateTOPUP.check(json).getValue());

        json = reproduceTOPUPInput();
        json.put("amount", -120003.234);
        assertFalse(validateTOPUP.check(json).getStatus().is2xxSuccessful());
        assertEquals("066", validateTOPUP.check(json).getValue());
    }

    private JSONObject reproduceTOPUPInput(){
        JSONObject json = new JSONObject();
        json.put("virtualNumber", "9030085794422971");
        json.put("amount", 200000);
        return json;
    }
}
