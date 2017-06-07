package ru.visualmath.android.api.model;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SyncLecture {

    @SerializedName("_id")
    private String id;

    @SerializedName("ongoingId")
    private String ongoingId;

    @SerializedName("isSpeaker")
    private boolean isSpeaker;

    @SerializedName("isOngoing")
    private boolean isOngoing;

    @SerializedName("lecture")
    private String lecture;

    @SerializedName("name")
    private String name;

    @SerializedName("created")
    private String created;

    public String getId() {
        return id;
    }

    public String getOngoingId() {
        return ongoingId;
    }

    public boolean isSpeaker() {
        return isSpeaker;
    }

    public boolean isOngoing() {
        return isOngoing;
    }

    public String getLecture() {
        return lecture;
    }

    public String getName() {
        return name;
    }

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
