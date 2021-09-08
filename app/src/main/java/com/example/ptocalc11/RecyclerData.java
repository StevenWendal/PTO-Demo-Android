package com.example.ptocalc11;

public class RecyclerData {
    // string for displaying
    private String ptodate;
    private String hours;

    // constructor for our date and hours.
    public RecyclerData(String date, String hours) {
        this.ptodate = date;
        this.hours = hours;
    }

    public RecyclerData(String date) {
        this.ptodate = date;
        this.hours = "8";
    }

    // creating getter and setter methods.
    public String getDate() {
        return ptodate;
    }

    public void setDate(String pto_date) {
        this.ptodate = pto_date;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }
}
