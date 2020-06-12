package com.MemberDomain.model.request;

public class OtpRequest {
    private String phoneNumber;

    public OtpRequest() {
    }

    public OtpRequest(String phoneNumber) {
        super();
        this.phoneNumber = phoneNumber;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append("User Phone Number = ").append(phoneNumber).append(" - ");

        return sb.toString();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

}