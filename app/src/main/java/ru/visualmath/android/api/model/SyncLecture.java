package ru.visualmath.android.api.model;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SyncLecture {

    @SerializedName("_id")
    public String id;

    public String ongoingId;

    public boolean isSpeaker;

    public boolean isOngoing;

    public String lecture;

    public String name;

    public String created;

    public Date getCreatedDate() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault());
        Date date = new Date(0);
        try {
            date = format.parse(created);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
