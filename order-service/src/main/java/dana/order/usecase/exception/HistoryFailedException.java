package dana.order.usecase.exception;

import dana.order.entity.DealsStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class HistoryFailedException extends ResponseStatusException {

    public HistoryFailedException(DealsStatus status){
        super(status.getStatus(), status.getMessage());
    }
}
