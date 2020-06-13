package com.MemberDomain.model.response;

import org.joda.time.DateTime;

public class OtpResponse {

    private String idOtp;
    private String idUser;
    private String otp;
    private DateTime expiredDate;

    public OtpResponse() {
    }

    public OtpResponse(String idOtp, String idUser, String otp, DateTime expiredDate) {
        super();
        this.idOtp = idOtp;
        this.idUser = idUser;
        this.otp = otp;
        this.expiredDate = expiredDate;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append("ID OTP = ").append(idOtp).append(" - ");
        sb.append("ID User = ").append(idUser).append(" - ");
        sb.append("OTP = ").append(otp).append(" - ");
        sb.append("Expired Date = ").append(expiredDate).append(" - ");

        return sb.toString();
    }

    public OtpResponse(String otp, String idUser, DateTime expiredDate){
        this("", idUser, otp, expiredDate);
    }

    public void setIdOtp(String idOtp) {
        this.idOtp = idOtp;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public void setExpiredDate(DateTime expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getIdOtp() {
        return idOtp;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getOtp() {
        return otp;
    }

    public DateTime getExpiredDate() {
        return expiredDate;
    }

}