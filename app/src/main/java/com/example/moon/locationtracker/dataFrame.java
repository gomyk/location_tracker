package com.example.moon.locationtracker;

public class dataFrame {
    public static final String TABLE_NAME = "Location";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_LONG = "longitude";
    public static final String COLUMN_LATI = "latitude";

    private int id;
    private String time;
    private double longitude;
    private double latitude;
    // Create table SQL query

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TIME + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
                    + COLUMN_LONG + " REAL,"
                    + COLUMN_LATI + " REAL"
                    + ")";

    public dataFrame() {
    }

    public dataFrame(int id, String time,double longitude, double latitude) {
        this.id = id;
        this.time = time;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String timestamp) {
        this.time = timestamp;
    }
    public double getLongitude(){
        return longitude;
    }
    public void setLongitude(double longitude){
        this.longitude = longitude;
    }
    public double getLatitude(){
        return latitude;
    }
    public void setLatitude(double latitude){
        this.latitude = latitude;
    }
}
