package com.okta.examples.model.status;

import org.springframework.http.HttpStatus;

public enum DealsStatus {

    REGISTRATION_SUCCESS("001", "Registration is successfull.", HttpStatus.CREATED),
    FILL_ALL_FORMS("002",  "Please fill in all the forms.", HttpStatus.BAD_REQUEST),
    NAME_INVALID("003", "Name is invalid.", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID("004", "Email is invalid.", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_INVALID("006", "Phone Number is invalid.", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID("005", "Password is invalid.", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTS("007", "Email already exists.", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_EXISTS("008", "Phone number already exists.", HttpStatus.BAD_REQUEST),
    PASSWORD_MISS_MATCH("009", "Password is missed match.", HttpStatus.BAD_REQUEST),
    LOGIN_SUCCESS("010", "You are logged in.", HttpStatus.OK),
    DATA_NOT_MATCH("011", "Your data do not match our records.", HttpStatus.BAD_REQUEST),
    REQUEST_OTP("012", "We have sent an OTP to your phone number.", HttpStatus.OK),
    PHONE_NUMBER_NOT_EXISTS("013", "The phone number does not exist.", HttpStatus.NOT_FOUND),
    OTP_MATCH("015", "OTP Match.", HttpStatus.OK),
    OTP_NOT_MATCH("016", "OTP not match", HttpStatus.BAD_REQUEST),
    OTP_EXPIRED("017", "Your OTP has expired.", HttpStatus.BAD_REQUEST),
    REQUEST_NEW_OTP("018", "Please request a new OTP!", HttpStatus.BAD_REQUEST),
    FORGOT_PASSWORD("019", "You have successfully changed your password.", HttpStatus.OK),
    PROFILE_COLLECTED("020", "Your profiles have been collected.", HttpStatus.OK),
    NOT_AUTHORIZED("021","You are not authorized.", HttpStatus.UNAUTHORIZED),
    USER_NOT_FOUND("022", "User not found.", HttpStatus.NOT_FOUND),
    PROFILE_UPDATED("023", "Successfully edit profile data", HttpStatus.OK),
    FILL_ONE_FIELD("024", "Please fill in at least one field!", HttpStatus.BAD_REQUEST),
    NEW_PASSWORD_MISS_MATCH("025", "Your new password and confirmation do not match.", HttpStatus.BAD_REQUEST),
    NEW_PASSWORD_INVALID("026", "Your new password is invalid.", HttpStatus.BAD_REQUEST),
    VOUCHER_NOT_AVAILABLE("027", "The voucher is currently not available.", HttpStatus.NOT_FOUND),
    PAYMENT_SUCCESS("028", "Your payment is successful.", HttpStatus.OK),
    BALANCE_NOT_ENOUGH("029", "Your balance is not enough.", HttpStatus.PAYMENT_REQUIRED),
    TRANSACTION_EXPIRED("031", "This payment has expired", HttpStatus.NOT_ACCEPTABLE),
    TRANSACTION_NOT_FOUND("032","Transaction is not found", HttpStatus.NOT_FOUND),
    TOPUP_SUCCESS("033", "Your TOPUP is successfull.", HttpStatus.CREATED),
    MAXIMUM_BALANCE("034", "Top-up failed! You have reached your maximum balance.", HttpStatus.FORBIDDEN),
    MINIMUM_TOPUP("035", "Minimum top-up amount is Rp 10.000,00",HttpStatus.FORBIDDEN),
    MERCHANT_NOT_AVAILABLE("036", "The merchant is currently not available for balance TOPUP.", HttpStatus.NOT_ACCEPTABLE),
    TRANSACTION_HISTORY_COLLECTED("037", "Transaction history are successfully collected", HttpStatus.OK),
    PAGE_NOT_FOUND("038", "Page not found.", HttpStatus.NOT_FOUND),
    VOUCHER_COLLECTED("040", "Vouchers are successfully collected.", HttpStatus.OK),
    LOGOUT_SUCCESS("041", "You are logged out.", HttpStatus.OK),
    VOUCHER_CREATED("042", "Create voucher successfully.", HttpStatus.CREATED),
    DATA_INVALID("043", "Your data is invalid.", HttpStatus.BAD_REQUEST),
    STATUS_CHANGE("044", "Successfully change status.", HttpStatus.OK),
    UPDATE_VOUCHER_FAILED("045", "We cannot update the voucher stock due to inactive voucher status.", HttpStatus.BAD_REQUEST),
    VOUCHER_DETAIL_COLLECTED("046", "Show detail voucher.", HttpStatus.OK),
    LOGIN_ANOTHER_DEVICE("047", "You login in another device", HttpStatus.OK),
    VOUCHER_OUT_OF_STOCK("048", "The voucher is currently out of stock.", HttpStatus.NOT_FOUND),
    TRANSACTION_CANT_PROCESS("049", "We cannot process your transaction for now. Please try again later!", HttpStatus.INTERNAL_SERVER_ERROR),
    FILL_OTP("051", "Please fill otp.", HttpStatus.BAD_REQUEST),
    TRANSACTION_CREATED("052","A new transaction has been created!", HttpStatus.CREATED),
    VOUCHER_NAME_EXISTS("053", "Voucher name is exist.", HttpStatus.BAD_REQUEST),
    ID_MERCHANT_NOAT_FOUND("054", "Id merchant not found.", HttpStatus.NOT_FOUND),
    FILL_VOUCHER_NAME("055", "Please fill form voucher name.", HttpStatus.BAD_REQUEST),
    FILL_DISCOUNT("056", "Please fill form discount.", HttpStatus.BAD_REQUEST),
    FILL_EXPIRED_DATE("057", "Please fill form expired date.", HttpStatus.BAD_REQUEST),
    FILL_MAX_DISCOUNT("058", "Please fill form max discount.", HttpStatus.BAD_REQUEST),
    FILL_QUOTA("059", "Please fill form quota.", HttpStatus.BAD_REQUEST),
    FILL_STATUS("060", "Please fill form status.", HttpStatus.BAD_REQUEST),
    FILL_VOUCHER_PRICE("061", "Please fill form voucher price.", HttpStatus.BAD_REQUEST),
    VOUCHER_NOT_FOUND("062", "Voucher not found.", HttpStatus.BAD_REQUEST),
    STATUS_INVALID("063", "Status invalid.", HttpStatus.BAD_REQUEST),
    FILL_SORT_CRITERIA("064", "Please fill sort criteria.", HttpStatus.BAD_REQUEST),
    VIRTUAL_ACCOUNT_INVALID("067", "Virtual Account is invalid", HttpStatus.BAD_REQUEST),
    AMOUNT_INVALID("066", "Amount is invalid.", HttpStatus.BAD_REQUEST),
    VOUCHER_NAME_INVALID("065", "Voucher name must be at least 3, and less than 20 characters.", HttpStatus.BAD_REQUEST),
    MAXIMUM_QUOTA("068", "Maximum voucher quota of 1000.", HttpStatus.BAD_REQUEST),
    EXPIRATION_DATE_INVALID("069", "Expiration date is maximum 1 month from today.", HttpStatus.BAD_REQUEST),
    FILL_MERCHANT_CATEGORY("070", "Please fill merchant category.", HttpStatus.BAD_REQUEST),
    WRONG_VA_TOPUP("071", "TOPUP failed! You have entered a wrong virtual number.", HttpStatus.NOT_FOUND),
    TRANSACTION_WRONG_USER("072", "The transaction does not belong to this user.", HttpStatus.BAD_REQUEST),
    ALREADY_FINISH_TRANSACTION("073", "There is nothing to do with an already finished transaction.", HttpStatus.NOT_ACCEPTABLE),
    HISTORY_INVALID_CATEGORY("074", "Category only consist of COMPLETED and IN-PROGRESS contnent.", HttpStatus.BAD_REQUEST),
    HISTORY_INVALID_PAGE("075", "Page parameter uses only numbers.", HttpStatus.BAD_REQUEST),
    HISTORY_INVALID_PAGINATION("076", "A page cannot have pagination numbers less than 0.", HttpStatus.BAD_REQUEST),
    HISTORY_INVALID_DATE("077", "Please use a valid date format (YYYY-mm-dd).", HttpStatus.BAD_REQUEST),
    HISTORY_FALSE_DATES("078", "Start date cannot be more current than end date.", HttpStatus.BAD_REQUEST),
    HISTORY_INVALID_SEARCH("079", "Maximum search per history is within 7 days.", HttpStatus.BAD_REQUEST),
    INVALID_TRANSACTION_ID("080", "Transaction ID is invalid", HttpStatus.BAD_REQUEST),
    INVALID_VOUCHER_ID("081", "Voucher ID is invalid", HttpStatus.BAD_REQUEST),
    REQUEST_TIME_OUT("082", "We cannot proccess your request for now. Please try again later", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String value;
    private final String message;
    private final HttpStatus status;

    DealsStatus(String value, String message, HttpStatus status) {
        this.value = value;
        this.message = message;
        this.status = status;
    }

    public String getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
