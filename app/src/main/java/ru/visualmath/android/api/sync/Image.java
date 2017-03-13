package ru.visualmath.android.api.sync;

public class Image {
    private String path;
    private Integer scale;

    public Image(String path, Integer scale) {
        this.path = path;
        this.scale = scale;
    }

    public String getPath() {
        return path;
    }

    public Integer getScale() {
        return scale;
    }

    @Override
    public String toString() {
        return "Image{" +
                "path=" + path +
                ", scale=" + scale +
                '}';
    }
}

