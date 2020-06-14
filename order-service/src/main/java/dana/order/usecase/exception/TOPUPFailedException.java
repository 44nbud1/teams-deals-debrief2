package dana.order.usecase.exception;

import dana.order.entity.DealsStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TOPUPFailedException extends ResponseStatusException {

    public TOPUPFailedException(DealsStatus status){
        super(status.getStatus(), status.getMessage());
    }

}
