package dana.order.entity;

import com.google.gson.Gson;
import org.json.simple.JSONObject;

public class Services {
    private Integer idService;
    private String serviceName;

    public Integer getIdService() {
        return idService;
    }

    public void setIdService(Integer idService) {
        this.idService = idService;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
