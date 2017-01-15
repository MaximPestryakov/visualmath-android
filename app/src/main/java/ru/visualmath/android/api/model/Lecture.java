package ru.visualmath.android.api.model;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Lecture {
    @SerializedName("_id")
    String id;

    String name;

    boolean hidden;

    String created;

    public String getName() {
        return name;
    }

    public Date getCreatedDate() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        Date date = new Date(0);
        try {
            date = format.parse(created);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
