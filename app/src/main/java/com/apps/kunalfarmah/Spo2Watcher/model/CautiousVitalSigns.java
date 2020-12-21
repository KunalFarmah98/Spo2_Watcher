package com.apps.kunalfarmah.Spo2Watcher.model;

public class CautiousVitalSigns {

    public static final int HIGH = 0;
    public static final int LOW = 1;

    public static final int BP = 0;
    public static final int HR = 1;
    public static final int O2 = 2;
    public static final int RR = 3;

    private int descriptive, vitalSign;

    public CautiousVitalSigns(int descr, int vitalSign) {
        this.descriptive = descr;
        this.vitalSign = vitalSign;
    }

    public int getDescriptive() {
        return descriptive;
    }

    public int getVitalSign() {
        return vitalSign;
    }

    public String getDescription(){
        switch(descriptive){
            case HIGH:
                switch (vitalSign){
                    case BP: return "High Blood Pressure";

                    case HR: return "High Heart Rate";

                    case O2: return "High Oxygen Saturation";

                    case RR: return "High Respiration Rate";
                }

            case LOW:
                switch (vitalSign){
                    case BP: return "Low Blood Pressure";

                    case HR: return "Low Heart Rate";

                    case O2: return "Low Oxygen Saturation";

                    case RR: return "Low Respiration Rate";
                }

            default: return "NULL";
        }
    }

    public String getSummary(){
        switch (vitalSign){
            case BP: return "Normal: 120/70 - 135/90";

            case HR: return "Normal: 60-90 beats per min";

            case O2: return "95-100";

            case RR: return "12-20";

        }

        return "NULL";
    }

}
