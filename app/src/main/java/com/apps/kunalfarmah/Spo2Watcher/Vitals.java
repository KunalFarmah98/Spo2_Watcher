package com.apps.kunalfarmah.Spo2Watcher;

public class Vitals {
    String  bp,hr,os,rr;

    public String getBp() {
        return bp;
    }

    public void setBp(String bp) {
        this.bp = bp;
    }

    public String getHr() {
        return hr;
    }

    public void setHr(String hr) {
        this.hr = hr;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getRr() {
        return rr;
    }

    public void setRr(String rr) {
        this.rr = rr;
    }

    public Vitals(){

    }

    public Vitals(String bp, String hr, String os, String rr) {
        this.bp = bp;
        this.hr = hr;
        this.os = os;
        this.rr = rr;
    }

    public Vitals(String hr, String os){
        this.hr = hr;
        this.os = os;
    }
}
