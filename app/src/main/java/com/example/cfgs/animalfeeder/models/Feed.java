package com.example.cfgs.animalfeeder.models;


import java.util.HashMap;
import java.util.Map;

public class Feed {
    String day;
    String hour;

    public Feed(String day, String hour) {
        this.day = day;
        this.hour = hour;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("dayStamp", day);
        result.put("timeStamp", hour);

        return result;
    }
}
