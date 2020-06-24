package dana.order.entity;

import com.google.gson.Gson;
import org.json.simple.JSONObject;

public class PaymentMethod {
    private Integer idPaymentMethod;
    private String methodName;

    public Integer getIdPaymentMethod() {
        return idPaymentMethod;
    }

    public void setIdPaymentMethod(Integer idPaymentMethod) {
        this.idPaymentMethod = idPaymentMethod;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
