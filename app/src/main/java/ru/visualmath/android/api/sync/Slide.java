package ru.visualmath.android.api.sync;

import java.util.ArrayList;

class Slide {
    private String name;
    private String content;
    private ArrayList<Image> imageList;

    Slide(String name, String content, ArrayList<Image> imageList) {
        this.name = name;
        this.content = content;
        this.imageList = imageList;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public ArrayList<Image> getImageList() {
        return imageList;
    }

    @Override
    public String toString() {
        int maxLength = content.length();
        if (maxLength > 25) {
            maxLength = 25;
        }
        return "Slide{" +
                "name='" + name + '\'' +
                ", content='" + content.substring(0, maxLength) + "... \'" +
                ", imageList=" + imageList +
                '}';
    }
}



