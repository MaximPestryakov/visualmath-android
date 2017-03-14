package ru.visualmath.android.api.sync;

public class Lecture {
    private String name;
    private String state;

    public String getState() {
        return state;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Lecture{" +
                "name='" + name + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}

