package dana.order.entity;

import com.google.gson.Gson;
import org.json.simple.JSONObject;

public class TransactionStatus {
    private Integer idTransactionStatus;
    private String statusName;

    public Integer getIdTransactionStatus() {
        return idTransactionStatus;
    }

    public void setIdTransactionStatus(Integer idTransactionStatus) {
        this.idTransactionStatus = idTransactionStatus;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
