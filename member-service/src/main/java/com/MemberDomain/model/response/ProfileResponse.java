package com.MemberDomain.model.response;

public class ProfileResponse {

    private String idUser;
    private String name;
    private String email;
    private String phoneNumber;
    private double balance;

    public ProfileResponse() {
    }

//    public ProfileResponse(String idUser, String name, String email, String phoneNumber) {
//        super();
//        this.idUser = idUser;
//        this.name = name;
//        this.email = email;
//        this.phoneNumber = phoneNumber;
//    }
//
//    public String toString(){
//        StringBuilder sb = new StringBuilder();
//
//        sb.append("ID User = ").append(idUser).append(" - ");
//        sb.append("User Name = ").append(name).append(" - ");
//        sb.append("User Email = ").append(email).append(" - ");
//        sb.append("User Phone Number = ").append(phoneNumber).append(" - ");
//
//        return sb.toString();
//    }
//
//    public ProfileResponse(String name, String email, String phoneNumber){
//        this("", name, email, phoneNumber);
//    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBalance(double balance){
        this.balance = balance;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public double getBalance(){
        return balance;
    }

}