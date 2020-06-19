package com.MemberDomain.model.request;

public class LoginRequest {
    private String phoneNumber;
    private String password;

    public LoginRequest() {
    }

//    public LoginRequest(String phoneNumber, String password) {
//        super();
//        this.phoneNumber = phoneNumber;
//        this.password = password;
//    }
//
//    public String toString(){
//        StringBuilder sb = new StringBuilder();
//
//        sb.append("User Phone Number = ").append(phoneNumber).append(" - ");
//        sb.append("User Password = ").append(password).append(" - ");
//
//        return sb.toString();
//    }
//
//    public LoginRequest(String password){
//        this("", password);
//    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

}