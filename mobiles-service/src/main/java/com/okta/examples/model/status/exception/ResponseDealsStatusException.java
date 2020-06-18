//package com.okta.examples.model.status.exception;
//
//import com.okta.examples.model.status.DealsStatus;
//import org.springframework.core.NestedRuntimeException;
//
//@SuppressWarnings("serial")
//public class ResponseDealsStatusException extends NestedRuntimeException {
//
//    private final DealsStatus status;
//
//    private final String reason;
//
//    public ResponseDealsStatusException(DealsStatus status) {
//        this(status, null, null);
//    }
//
//    public ResponseDealsStatusException(DealsStatus status, String reason, Throwable cause) {
//        super(status.getMessage(), cause);
//        this.status = status;
//        this.reason = reason;
//    }
//
//    public DealsStatus getStatus() {
//        return this.status;
//    }
//
//    public String getReason() {
//        return reason;
//    }
//
//
//}
