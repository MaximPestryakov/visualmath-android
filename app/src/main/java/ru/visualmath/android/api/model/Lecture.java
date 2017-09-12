package ru.visualmath.android.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Lecture implements Serializable {

    @SerializedName("_id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("mapping")
    private List<Page> mapping;

    @SerializedName("modules")
    private List<Module> modules;

    @SerializedName("questions")
    private List<Question> questions;

    @SerializedName("questionBlocks")
    private List<String> questionBlocks;

    @SerializedName("hidden")
    private boolean hidden;

    @SerializedName("created")
    private String created;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Page> getMapping() {
        return mapping;
    }

    public List<Module> getModules() {
        return modules;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public List<String> getQuestionBlocks() {
        return questionBlocks;
    }

    public boolean isHidden() {
        return hidden;
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
