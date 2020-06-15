package dana.order.usecase.validate;

import dana.order.entity.DealsStatus;
import dana.order.usecase.exception.HistoryFailedException;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class ValidateTransactionHistory {

    public DealsStatus check(JSONObject json){

        if (json.get("category") != null){
            if ((""+json.get("category")).equals("COMPLETED") || (""+json.get("category")).equals("IN-PROGRESS")){
                // skip
            }else {
                return DealsStatus.HISTORY_INVALID_CATEGORY;
            }
        }

        if (json.get("page") != null){
            if (checkPage(""+json.get("page")) == Boolean.FALSE){
                return DealsStatus.HISTORY_INVALID_PAGE;
            }

            Integer page = Integer.valueOf(""+json.get("page"));
            if (page < 0){
                return DealsStatus.HISTORY_INVALID_PAGINATION;
            }
        }

        if (json.get("startDate") != null){
            if (checkDate(""+json.get("startDate")) == Boolean.FALSE){
                return DealsStatus.HISTORY_INVALID_DATE;
            }
        }

        if (json.get("endDate") != null){
            if (checkDate(""+json.get("endDate")) == Boolean.FALSE){
                return DealsStatus.HISTORY_INVALID_DATE;
            }
        }

        if (json.get("startDate") != null && json.get("endDate") != null){

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = null, endDate = null;

            try {
                startDate = df.parse(""+json.get("startDate"));
                endDate = df.parse(""+json.get("endDate"));
            } catch (ParseException e) {e.printStackTrace();}

            if (startDate.after(endDate)){
                return DealsStatus.HISTORY_FALSE_DATES;
            }

            // Maximum search for history is within 7 days
            Calendar testCalendar = Calendar.getInstance();
            testCalendar.setTime(startDate);
            testCalendar.add(Calendar.DATE, 7);
            Date testDate = null;

            try{
                testDate = df.parse(""+df.format(testCalendar.getTime()));
            }catch (ParseException e){e.printStackTrace();}

            if (endDate.after(testDate)){
                return DealsStatus.HISTORY_INVALID_SEARCH;
            }
        }

        return DealsStatus.OK;
    }

    public Boolean checkPage(String page){
        String regex = "^[\\d-]+$";
        if(!Pattern.matches(regex, page)){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Boolean checkDate(String date){
        String regex = "^[\\d]{4}[-]{1}[\\d]{2}[-]{1}[\\d]{2}+$";
        if(!Pattern.matches(regex, date)){
            return Boolean.FALSE;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setLenient(false);
        try {
            df.parse(date);
            return Boolean.TRUE;
        } catch (ParseException e) {
            return Boolean.FALSE;
        }
    }
}
