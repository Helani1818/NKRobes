package com.example.androidapp.ViewHolder;

public class Message {
    public String messageType;
    public String message;
    public String messageTime;
    public String user;
    public boolean from;
    public String image;
    public String toUser;

    public Message(String messageType, String message, String messageTime, String user, String image) {
        this.messageType = messageType;
        this.message = message;
        this.messageTime = messageTime;
        this.user = user;
        this.image = image;
    }

    public boolean isFrom() {
        return from;
    }

    public void setFrom(boolean from) {
        this.from = from;
    }

    public Message() {
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageType='" + messageType + '\'' +
                ", message='" + message + '\'' +
                ", messageTime='" + messageTime + '\'' +
                ", user='" + user + '\'' +
                ", from=" + from +
                ", image='" + image + '\'' +
                '}';
    }
}