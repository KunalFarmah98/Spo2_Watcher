package com.apps.kunalfarmah.Spo2Watcher.model;

public class Patient {
    String name,age,gender,patientId,mobile;
    boolean active;

    public Patient(String name, String age, String gender, String patientId, String mobile, boolean active) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.patientId = patientId;
        this.mobile = mobile;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
