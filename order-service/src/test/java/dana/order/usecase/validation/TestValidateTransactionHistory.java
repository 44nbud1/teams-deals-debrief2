package dana.order.usecase.validation;

import dana.order.usecase.validate.ValidateTransactionHistory;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestValidateTransactionHistory {

    @Autowired
    ValidateTransactionHistory validateTransactionHistory;

    @Test
    public void contextLoads() throws Exception {
        assertThat(validateTransactionHistory).isNotNull();
    }

    @Test
    void validateCheckDate() {
        assertEquals(true, validateTransactionHistory.checkDate("2020-06-12"));
        assertEquals(false, validateTransactionHistory.checkDate("2020-06-2jy"));
        assertEquals(false, validateTransactionHistory.checkDate("2020-20-45"));
        assertEquals(false, validateTransactionHistory.checkDate("2020/06/12"));
    }

    @Test
    void validateCheckPage(){
        assertEquals(true, validateTransactionHistory.checkPage("12"));
        assertEquals(true, validateTransactionHistory.checkPage("-20"));
        assertEquals(false, validateTransactionHistory.checkPage("2-0"));
        assertEquals(false, validateTransactionHistory.checkPage("4.5"));
        assertEquals(false, validateTransactionHistory.checkPage("abc"));
    }

    @Test
    void validateTransactionHistoryCheck(){

        JSONObject json = reproduceHistoryInput();
        assertTrue(validateTransactionHistory.check(json).getStatus().is2xxSuccessful());
        assertEquals("083", validateTransactionHistory.check(json).getValue());

        json = reproduceHistoryInput();
        json.put("category", "ANY VALUES");
        assertFalse(validateTransactionHistory.check(json).getStatus().is2xxSuccessful());
        assertEquals("074", validateTransactionHistory.check(json).getValue());

        json = reproduceHistoryInput();
        json.put("startDate", "2020-03-30");
        assertFalse(validateTransactionHistory.check(json).getStatus().is2xxSuccessful());
        assertEquals("078", validateTransactionHistory.check(json).getValue());

        json = reproduceHistoryInput();
        json.put("endDate", "ANY VALUES");
        assertFalse(validateTransactionHistory.check(json).getStatus().is2xxSuccessful());
        assertEquals("077", validateTransactionHistory.check(json).getValue());

        json = reproduceHistoryInput();
        json.put("page", "one");
        assertFalse(validateTransactionHistory.check(json).getStatus().is2xxSuccessful());
        assertEquals("075", validateTransactionHistory.check(json).getValue());

        json = reproduceHistoryInput();
        json.put("page", "-15");
        assertFalse(validateTransactionHistory.check(json).getStatus().is2xxSuccessful());
        assertEquals("076", validateTransactionHistory.check(json).getValue());

        json = reproduceHistoryInput();
        json.remove("startDate");
        assertTrue(validateTransactionHistory.check(json).getStatus().is2xxSuccessful());
        assertEquals("083", validateTransactionHistory.check(json).getValue());

        json = reproduceHistoryInput();
        json.put("endDate", "yyyy-mm-dd");
        assertFalse(validateTransactionHistory.check(json).getStatus().is2xxSuccessful());
        assertEquals("077", validateTransactionHistory.check(json).getValue());

        json = reproduceHistoryInput();
        json.put("endDate", "2020-12-14fers");
        assertFalse(validateTransactionHistory.check(json).getStatus().is2xxSuccessful());
        assertEquals("077", validateTransactionHistory.check(json).getValue());
    }

    private JSONObject reproduceHistoryInput(){
        JSONObject json = new JSONObject();
        json.put("idUser", "1");
        json.put("category", "COMPLETED");
        json.put("startDate", "2020-01-28");
        json.put("endDate", "2020-02-02");
        json.put("page", 2);

        return json;
    }
}
