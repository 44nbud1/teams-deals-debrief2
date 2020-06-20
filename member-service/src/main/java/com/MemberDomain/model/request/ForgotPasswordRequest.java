package com.MemberDomain.model.request;

public class ForgotPasswordRequest {
    private String newPassword;
    private String confirmPassword;

    public ForgotPasswordRequest() {
    }

//    public ForgotPasswordRequest(String newPassword, String confirmPassword) {
//        super();
//        this.newPassword = newPassword;
//        this.confirmPassword = confirmPassword;
//    }
//
//    public String toString(){
//        StringBuilder sb = new StringBuilder();
//
//        sb.append("User New Password = ").append(newPassword).append(" - ");
//        sb.append("Confirm Password = ").append(confirmPassword).append(" - ");
//
//        return sb.toString();
//    }
//
//    public ForgotPasswordRequest(String confirmPassword){
//        this("", confirmPassword);
//    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}