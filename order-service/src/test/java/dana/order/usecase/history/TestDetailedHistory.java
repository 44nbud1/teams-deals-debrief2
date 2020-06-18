package dana.order.usecase.history;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestDetailedHistory {

    @Autowired
    DetailedTransactionHistory detailedTransactionHistory;

    @Test
    public void contextLoads() throws Exception {
        assertThat(detailedTransactionHistory).isNotNull();
    }

    @Test
    public void testDetailedHistory(){
        JSONObject json = reproduceDetailedHistory();
        assertEquals(true, detailedTransactionHistory.get(json).getStatusCode().is2xxSuccessful());

        json.put("idTransaction", 11);
        assertEquals(true, detailedTransactionHistory.get(json).getStatusCode().is4xxClientError());

        json.put("idUser", 0);
        assertEquals(true, detailedTransactionHistory.get(json).getStatusCode().is4xxClientError());
    }

    private JSONObject reproduceDetailedHistory(){
        JSONObject json = new JSONObject();
        json.put("idUser", 9);
        json.put("idTransaction", 1);

        return json;
    }

}
