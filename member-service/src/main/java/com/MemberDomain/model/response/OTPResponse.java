package com.MemberDomain.model.response;

import org.joda.time.DateTime;

import java.util.Date;

public class OTPResponse {

    private String idOtp;
    private String otp;
    private DateTime date;
    private int is_active;
    private String idUser;

    public OTPResponse() {
    }

    public OTPResponse(String idOtp, String otp, DateTime date, int is_active, String idUser) {
        super();
        this.idOtp = idOtp;
        this.otp = otp;
        this.date = date;
        this.is_active = is_active;
        this.idUser = idUser;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append("ID OTP = ").append(idOtp).append(" - ");
        sb.append("OTP = ").append(otp).append(" - ");
        sb.append("Date Active = ").append(date).append(" - ");
        sb.append("Active Status = ").append(is_active).append(" - ");
        sb.append("ID User = ").append(idUser).append(" - ");

        return sb.toString();
    }

    public OTPResponse(String otp, DateTime date, int is_active, String idUser){
        this("", otp, date, is_active, idUser);
    }

    public void setIdOtp(String idOtp) {
        this.idOtp = idOtp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdOtp() {
        return idOtp;
    }

    public String getOtp() {
        return otp;
    }

    public DateTime getDate() {
        return date;
    }

    public int getIs_active() {
        return is_active;
    }

    public String getIdUser() {
        return idUser;
    }

}