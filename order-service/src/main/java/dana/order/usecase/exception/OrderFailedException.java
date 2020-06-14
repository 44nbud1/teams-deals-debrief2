package dana.order.usecase.exception;

import dana.order.entity.DealsStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class OrderFailedException extends ResponseStatusException {

    public OrderFailedException(DealsStatus status){
        super(status.getStatus(), status.getMessage());
    }

}
