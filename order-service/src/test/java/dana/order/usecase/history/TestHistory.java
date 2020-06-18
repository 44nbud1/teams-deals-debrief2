package dana.order.usecase.history;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestHistory {

    @Autowired
    TransactionHistory transactionHistory;

    @Test
    public void contextLoads() throws Exception {
        assertThat(transactionHistory).isNotNull();
    }

    @Test
    void testTransactionHistory(){
        JSONObject json = reproduceTransactionInput();
        assertEquals(true, transactionHistory.get(json).getStatusCode().is2xxSuccessful());

        json.put("startDate", "2020/05/12");
        assertEquals(true, transactionHistory.get(json).getStatusCode().is4xxClientError());

        json.put("category", "NOTFOUND");
        assertEquals(true, transactionHistory.get(json).getStatusCode().is4xxClientError());

        json.put("page", "-34");
        assertEquals(true, transactionHistory.get(json).getStatusCode().is4xxClientError());

        json.put("startDate", "2020-06-01");
        assertEquals(true, transactionHistory.get(json).getStatusCode().is4xxClientError());
    }

    private JSONObject reproduceTransactionInput(){
        JSONObject json = new JSONObject();
        json.put("idUser", 9);
        json.put("category", "COMPLETED");
        json.put("startDate", "2020-05-12");
        json.put("endDate", "2020-05-16");
        json.put("page", "0");
        return json;
    }
}
