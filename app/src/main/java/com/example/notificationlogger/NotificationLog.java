package com.example.notificationlogger;

public class NotificationLog {
    private String packageName;
    private String title;
    private String text;
    private String sender;
    private long timestamp;

    public NotificationLog(String packageName, String title, String text, String sender, long timestamp) {
        this.packageName = packageName;
        this.title = title;
        this.text = text;
        this.sender = sender;
        this.timestamp = timestamp;
    }

    public String getPackageName() { return packageName; }
    public String getTitle() { return title; }
    public String getText() { return text; }
    public String getSender() { return sender; }
}