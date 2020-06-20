package com.MemberDomain.model.response;

public class UserDataResponse {

    private String idUser;
    private String name;
    private String email;
    private String phoneNumber;
    private double balance;
    private String idRole;
    private String roleName;

    public UserDataResponse() {
    }

//    public UserDataResponse(String idUser, String name, String email, String phoneNumber, double balance, String idRole, String roleName) {
//        super();
//        this.idUser = idUser;
//        this.name = name;
//        this.email = email;
//        this.phoneNumber = phoneNumber;
//        this.balance = balance;
//        this.idRole = idRole;
//        this.roleName = roleName;
//    }
//
//    public String toString(){
//        StringBuilder sb = new StringBuilder();
//
//        sb.append("ID User = ").append(idUser).append(" - ");
//        sb.append("User Name = ").append(name).append(" - ");
//        sb.append("User Email = ").append(email).append(" - ");
//        sb.append("User Phone Number = ").append(phoneNumber).append(" - ");
//        sb.append("User Balance = ").append(balance).append(" - ");
//        sb.append("ID Role = ").append(idRole).append(" - ");
//        sb.append("Role Name = ").append(roleName).append(" - ");
//
//        return sb.toString();
//    }
//
//    public UserDataResponse(String name, String email, String phoneNumber, double balance, String idRole, String roleName){
//        this("", name, email, phoneNumber, balance, idRole, roleName);
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

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setIdRole(String idRole) {
        this.idRole = idRole;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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

    public double getBalance() {
        return balance;
    }

    public String getIdRole() {
        return idRole;
    }

    public String getRoleName() {
        return roleName;
    }
}