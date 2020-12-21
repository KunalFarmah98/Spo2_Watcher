package com.apps.kunalfarmah.Spo2Watcher.model;

import java.util.Date;

public class ChatMessage {
    private String messageText;
    private MessageUser messageUser;

    public ChatMessage(String messageText, MessageUser messageUser) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        messageTime = new Date().getTime();

    }

    private long messageTime;


    public ChatMessage(){

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public MessageUser getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(MessageUser messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

}
