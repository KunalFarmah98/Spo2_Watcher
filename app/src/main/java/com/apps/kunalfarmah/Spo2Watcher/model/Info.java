package com.apps.kunalfarmah.Spo2Watcher.model;

public class Info {

    String topic;
    String info;

    public Info(String topic, String info) {
        this.topic = topic;
        this.info = info;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

