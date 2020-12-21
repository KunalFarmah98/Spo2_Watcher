package com.apps.kunalfarmah.Spo2Watcher.model;

public class Remedy {

    String remedies,remedy_title;
    int images;


    public String getRemedy_title() {
        return remedy_title;
    }

    public void setRemedy_title(String remedy_title) {
        this.remedy_title = remedy_title;
    }

    public Remedy(String remedy_title , String remedies, int images) {
        this.remedies = remedies;
        this.images = images;
        this.remedy_title = remedy_title;
    }

    public String getRemedies() {
        return remedies;
    }

    public void setRemedies(String remedies) {
        this.remedies = remedies;
    }

    public int getImages() {
        return images;
    }

    public void setImages(int images) {
        this.images = images;
    }
}
