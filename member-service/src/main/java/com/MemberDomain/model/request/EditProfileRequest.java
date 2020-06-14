package com.MemberDomain.model.request;

public class EditProfileRequest {

    private String name;
    private String email;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

    public EditProfileRequest() {
    }

    public EditProfileRequest(String name, String email, String oldPassword, String newPassword, String confirmPassword) {
        super();
        this.name = name;
        this.email = email;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append("User Name = ").append(name).append(" - ");
        sb.append("User Email = ").append(email).append(" - ");
        sb.append("User Old Password = ").append(oldPassword).append(" - ");
        sb.append("User New Password = ").append(newPassword).append(" - ");
        sb.append("Confirm New Password = ").append(confirmPassword).append(" - ");

        return sb.toString();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getName() {
        return name;
    }


    public String getEmail() {
        return email;
    }


    public String getOldPassword() {
        return oldPassword;
    }


    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}

