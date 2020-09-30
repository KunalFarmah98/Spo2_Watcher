package com.apps.kunalfarmah.Spo2Watcher;

public class DoctorDetails {
    private String name,qualification,address, specialities, doctorID, hospital;
    private int messageCharge, appointmentCharge;
    private float lat, lang;

    public DoctorDetails() {
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    DoctorDetails(String name, String doctorID, String qualification, String address, String specialities, String hospital, int messageCharge, int appointmentCharge, float lat, float lang) {
        this.name = name;
        this.doctorID = doctorID;
        this.qualification = qualification;
        this.address = address;
        this.specialities = specialities;
        this.messageCharge = messageCharge;
        this.appointmentCharge = appointmentCharge;
        this.lat = lat;
        this.lang = lang;
        this.hospital = hospital;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLang() {
        return lang;
    }

    public void setLang(float lang) {
        this.lang = lang;
    }

    public String getSpecialities() {
        return specialities;
    }

    public void setSpecialities(String specialities) {
        this.specialities = specialities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMessageCharge() {
        return messageCharge;
    }

    public void setMessageCharge(int messageCharge) {
        this.messageCharge = messageCharge;
    }

    public int getAppointmentCharge() {
        return appointmentCharge;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public void setAppointmentCharge(int appointmentCharge) {
        this.appointmentCharge = appointmentCharge;
    }
}
