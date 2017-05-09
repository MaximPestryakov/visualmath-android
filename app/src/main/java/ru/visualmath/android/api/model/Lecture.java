package ru.visualmath.android.api.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Lecture implements Serializable {

    public String name;

    public List<Page> mapping;

    public List<Module> modules;

    public List<Question> questions;

    public List<String> questionBlocks;

    public boolean hidden;

    public String created;

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
