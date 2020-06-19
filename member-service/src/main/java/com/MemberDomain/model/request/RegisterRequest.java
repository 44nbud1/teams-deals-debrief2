package com.MemberDomain.model.request;
public class RegisterRequest {

    private String idUser;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private String confirmPassword;

    public RegisterRequest() {
    }

//    public RegisterRequest(String idUser, String name, String email, String phoneNumber, String password,
//                           String confirmPassword) {
//        super();
//        this.idUser = idUser;
//        this.name = name;
//        this.email = email;
//        this.phoneNumber = phoneNumber;
//        this.password = password;
//        this.confirmPassword = confirmPassword;
//    }
//
//    public String toString(){
//        StringBuilder sb = new StringBuilder();
//
//        sb.append("ID User = ").append(idUser).append(" - ");
//        sb.append("User Name = ").append(name).append(" - ");
//        sb.append("User Email = ").append(email).append(" - ");
//        sb.append("User Phone Number = ").append(phoneNumber).append(" - ");
//        sb.append("User Password = ").append(password).append(" - ");
//        sb.append("Confirm Password = ").append(confirmPassword).append(" - ");
//
//        return sb.toString();
//    }
//
//    public RegisterRequest(String name, String email, String phoneNumber, String password, String confirmPassword){
//        this("", name, email, phoneNumber, password, confirmPassword);
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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

}