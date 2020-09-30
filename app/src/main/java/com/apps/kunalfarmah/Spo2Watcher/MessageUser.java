package com.apps.kunalfarmah.Spo2Watcher;

public class MessageUser {

    private String userName;
    private String userID;
    private String emailID;

    public MessageUser(){

    }

    public MessageUser(String userName, String userID, String emailID) {
        this.userName = userName;
        this.userID = userID;
        this.emailID = emailID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

}
