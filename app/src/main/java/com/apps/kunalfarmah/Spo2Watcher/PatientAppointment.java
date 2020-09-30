package com.apps.kunalfarmah.Spo2Watcher;

public class PatientAppointment {

    private String patientName;
    private String doctorName;
    private String patientID;
    private String doctorID;
    private int active;
    private boolean done;

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    private String mobile;
    private Time appointmentTime;
    private String gender;
    private int age, confirmed;
    private String description;
    private Vitals vitals;

    public PatientAppointment(){}


    public PatientAppointment(String patientName, String doctorName, String patientID, String doctorID,
                              Time appointmentTime, String gender, int age, String description, Vitals vitals) {
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.appointmentTime = appointmentTime;
        this.gender = gender;
        this.age = age;
        this.description = description;
        this.confirmed = 0;
        this.vitals = vitals;
    }
    public PatientAppointment(String patientName, String doctorName, String patientID, String doctorID,
                              Time appointmentTime, String gender, int age, String description, String mobile, Vitals vitals) {
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.appointmentTime = appointmentTime;
        this.gender = gender;
        this.age = age;
        this.description = description;
        this.confirmed = 0;
        this.vitals = vitals;
        this.mobile = mobile;
        this.active = 1;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Time getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Time appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getTime(){
        int hr = appointmentTime.getHour();
        int mm = appointmentTime.getMinutes();

        String HH;
        if(hr<10){
            HH = "0"+hr;
        }
        else HH = String.valueOf( hr);

        String MM;
        if(mm<10){
            MM = "0"+mm;
        }
        else{
            MM = String.valueOf(mm);
        }

        return HH+" : "+MM;
    }

    public String getDate(){
        return appointmentTime.getDay()+"."+appointmentTime.getMonth()+"."+appointmentTime.getYear();
    }

    public Vitals getVitals() {
        return vitals;
    }

    public void setVitals(Vitals vitals) {
        this.vitals = vitals;
    }
}
