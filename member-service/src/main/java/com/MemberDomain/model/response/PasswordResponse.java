package com.MemberDomain.model.response;

public class PasswordResponse{
    private String password;

    public PasswordResponse() {
    }

    public PasswordResponse(String password) {
        super();
        this.password = password;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append("User Password = ").append(password).append(" - ");

        return sb.toString();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}