package dana.order.usecase.exception;

import dana.order.entity.DealsStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TOPUPFailedException extends ResponseStatusException {

    private String internalStatusCode;

    public TOPUPFailedException(DealsStatus status){
        super(status.getStatus(), status.getMessage());
        this.internalStatusCode = status.getValue();
    }

    public String getInternalStatusCode() {
        return internalStatusCode;
    }

    public void setInternalStatusCode(String internalStatusCode) {
        this.internalStatusCode = internalStatusCode;
    }
}
