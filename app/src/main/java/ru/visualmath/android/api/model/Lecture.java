package ru.visualmath.android.api.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Lecture {

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
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        Date date = new Date(0);
        try {
            date = format.parse(created);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public class Page {
        public int index;

        public String type;
    }

    public class Module {
        public String name;

        public String content;

        public List<String> images;
    }

    public class Question {
        public String question;

        public boolean multiple;

        public List<String> answers;

        public List<String> images;
    }
}
