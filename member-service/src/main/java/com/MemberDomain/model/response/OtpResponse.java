package com.MemberDomain.model.response;

import org.joda.time.DateTime;

public class OtpResponse {

    private String idOtp;
    private String idUser;
    private String otp;
    private DateTime expiredDate;
    private int matchStatus;

    public OtpResponse() {
    }

//    public OtpResponse(String idOtp, String idUser, String otp, DateTime expiredDate, int matchStatus) {
//        super();
//        this.idOtp = idOtp;
//        this.idUser = idUser;
//        this.otp = otp;
//        this.expiredDate = expiredDate;
//        this.matchStatus = matchStatus;
//    }
//
//    public String toString(){
//        StringBuilder sb = new StringBuilder();
//
//        sb.append("ID OTP = ").append(idOtp).append(" - ");
//        sb.append("ID User = ").append(idUser).append(" - ");
//        sb.append("OTP = ").append(otp).append(" - ");
//        sb.append("Expired Date = ").append(expiredDate).append(" - ");
//        sb.append("Match Status = ").append(matchStatus).append(" - ");
//
//        return sb.toString();
//    }
//
//    public OtpResponse(String idUser, String otp, DateTime expiredDate, int matchStatus){
//        this("", idUser, otp, expiredDate, matchStatus);
//    }

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

    public void setMatchStatus(int matchStatus) {
        this.matchStatus = matchStatus;
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

    public int getMatchStatus() {
        return matchStatus;
    }
}