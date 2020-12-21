package com.apps.kunalfarmah.Spo2Watcher.model;

public class ChatListUser extends MessageUser {

    private long lastMessageTime;
    private String lastMessage;
    private boolean unreadMessages;
    private boolean online;
    private int favourite;

    public ChatListUser(MessageUser messageUser, long lastMessageTime, String lastMessage, boolean unreadMessages, boolean online, int favourite) {
        super(messageUser.getUserName(), messageUser.getUserID(), messageUser.getEmailID());
        this.lastMessageTime = lastMessageTime;
        this.lastMessage = lastMessage;
        this.unreadMessages = unreadMessages;
        this.online = online;
        this.favourite = favourite;
    }

    public ChatListUser(MessageUser messageUser, long lastMessageTime, String lastMessage, boolean unreadMessages, int favourite) {
        super(messageUser.getUserName(), messageUser.getUserID(), messageUser.getEmailID());
        this.lastMessageTime = lastMessageTime;
        this.lastMessage = lastMessage;
        this.unreadMessages = unreadMessages;
        this.favourite = favourite;
        online = false;
    }


    public ChatListUser(){

    }

    public ChatListUser(MessageUser messageUser, long lastMessageTime, String lastMessage, int favourite){
        super(messageUser.getUserName(), messageUser.getUserID(), messageUser.getEmailID());
        this.lastMessageTime = lastMessageTime;
        this.lastMessage = lastMessage;
        this.favourite = favourite;
        unreadMessages = false;
        online = false;
    }


    public long getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(long lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void reverseTime(){
        lastMessageTime = -1* lastMessageTime;
    }

    public boolean getUnreadMessages() {
        return unreadMessages;
    }

    public void setUnreadMessages(boolean unreadMessages) {
        this.unreadMessages = unreadMessages;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public int getFavourite() {
        return favourite;
    }

    public void setFavourite(int favourite) {
        this.favourite = favourite;
    }
}
