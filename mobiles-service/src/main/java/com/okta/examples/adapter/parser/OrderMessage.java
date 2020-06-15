package com.okta.examples.adapter.parser;

import org.springframework.stereotype.Service;
import com.okta.examples.model.status.DealsStatus;

@Service
public class OrderMessage {

    public String getStatus(String message){
        String status = "";
        switch (message){
            case "Please fill in all the forms.":
                status = DealsStatus.FILL_ALL_FORMS.getValue();
                break;
            case "The voucher is currently not available.":
                status = DealsStatus.VOUCHER_NOT_AVAILABLE.getValue();
                break;
            case "User is not found.":
                status = DealsStatus.USER_NOT_FOUND.getValue();
                break;
            case "Your balance is not enough.":
                status = DealsStatus.BALANCE_NOT_ENOUGH.getValue();
                break;
            case "This payment has expired":
                status = DealsStatus.TRANSACTION_EXPIRED.getValue();
                break;
            case "Transaction is not found":
                status = DealsStatus.TRANSACTION_NOT_FOUND.getValue();
                break;
            case "Top-up failed! You have reached your maximum balance.":
                status = DealsStatus.MAXIMUM_BALANCE.getValue();
                break;
            case "The merchant is currently not available for balance TOPUP.":
                status = DealsStatus.MERCHANT_NOT_AVAILABLE.getValue();
                break;
            case "Minimum top-up amount is Rp 10.000,00":
                status = DealsStatus.MINIMUM_TOPUP.getValue();
                break;
            case "We cannot process your transaction for now. Please try again later!":
                status = DealsStatus.TRANSACTION_CANT_PROCESS.getValue();
                break;
            case "The voucher is currently out of stock.":
                status = DealsStatus.VOUCHER_OUT_OF_STOCK.getValue();
                break;
            case "Virtual Account is invalid":
                status = DealsStatus.VIRTUAL_ACCOUNT_INVALID.getValue();
                break;
            case "Amount is invalid.":
                status = DealsStatus.AMOUNT_INVALID.getValue();
                break;
            case "TOPUP failed! You have entered a wrong virtual number.":
                status = DealsStatus.WRONG_VA_TOPUP.getValue();
                break;
            case "The transaction does not belong to this user.":
                status = DealsStatus.TRANSACTION_WRONG_USER.getValue();
                break;
            case "There is nothing to do with an already finished transaction.":
                status = DealsStatus.ALREADY_FINISH_TRANSACTION.getValue();
                break;
            case "Category only consist of COMPLETED and IN-PROGRESS contnent.":
                status = DealsStatus.HISTORY_INVALID_CATEGORY.getValue();
                break;
            case "Page parameter uses only numbers.":
                status = DealsStatus.HISTORY_INVALID_PAGE.getValue();
                break;
            case "A page cannot have pagination numbers less than 0.":
                status = DealsStatus.HISTORY_INVALID_PAGINATION.getValue();
                break;
            case "Please use a valid date format (YYYY-mm-dd).":
                status = DealsStatus.HISTORY_INVALID_DATE.getValue();
                break;
            case "Start date cannot be more current than end date.":
                status = DealsStatus.HISTORY_FALSE_DATES.getValue();
                break;
            case "Maximum search per history is within 7 days":
                status = DealsStatus.HISTORY_INVALID_SEARCH.getValue();
                break;
            case "Transaction ID is invalid.":
                status = DealsStatus.INVALID_TRANSACTION_ID.getValue();
                break;
            case "Voucher ID is invalid.":
                status = DealsStatus.INVALID_VOUCHER_ID.getValue();
                break;
            default:
                status = DealsStatus.DATA_INVALID.getValue();
        }
        return status;
    }
}
