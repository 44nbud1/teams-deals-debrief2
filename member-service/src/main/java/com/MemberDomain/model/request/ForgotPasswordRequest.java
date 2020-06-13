package com.MemberDomain.model.request;

public class ForgotPasswordRequest {
    private String password;
    private String confirmPassword;

    public ForgotPasswordRequest() {
    }

    public ForgotPasswordRequest(String password, String confirmPassword) {
        super();
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append("User Password = ").append(password).append(" - ");
        sb.append("Confirm Password = ").append(confirmPassword).append(" - ");

        return sb.toString();
    }

    public ForgotPasswordRequest(String confirmPassword){
        this("", confirmPassword);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}