package ru.visualmath.android.api.model;

import com.google.gson.annotations.SerializedName;

public class SyncLecture {

    @SerializedName("_id")
    public String id;

    public String ongoingId;

    public boolean isSpeaker;

    public boolean isOngoing;

    public String lecture;

    public String name;

    public String created;
}
