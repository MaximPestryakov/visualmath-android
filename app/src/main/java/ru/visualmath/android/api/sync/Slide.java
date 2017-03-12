package ru.visualmath.android.api.sync;

class Slide {
    private String name;
    private String content;

    Slide(String name, String content) {
        this.name = name;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Slide{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}

