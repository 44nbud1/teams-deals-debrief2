package dana.order.adapter.wrapper;

import dana.order.entity.DealsStatus;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ResponseWrapper {
    public static ResponseEntity<?> wrap(DealsStatus dealsStatus, Object data, String path){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        JSONObject result = new JSONObject();

        result.put("timestamp", sdf.format(new Date()));
        result.put("message", dealsStatus.getMessage());
        result.put("data", data);
        result.put("status", dealsStatus.getValue());
        result.put("path", path);

        return new ResponseEntity<>(result, dealsStatus.getStatus());
    }

    public static JSONObject jsonWrap(DealsStatus dealsStatus, Object data, String path){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        JSONObject result = new JSONObject();

        result.put("timestamp", sdf.format(new Date()));
        result.put("message", dealsStatus.getMessage());
        result.put("data", data);
        result.put("status", dealsStatus.getValue());
        result.put("path", path);

        return result;
    }

}
