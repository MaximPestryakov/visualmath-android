package ru.visualmath.android.api;

public class Slide {
    private String name;
    private String content;
    public Slide(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String toString() {
        return this.name + this.content;
    }
}
