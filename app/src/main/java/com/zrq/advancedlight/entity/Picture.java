package com.zrq.advancedlight.entity;

public class Picture {
    private String title;
    private String path;
    private long date;

    public Picture() {
    }

    public Picture(String title, String path, long date) {
        this.title = title;
        this.path = path;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "title='" + title + '\'' +
                ", path='" + path + '\'' +
                ", date=" + date +
                '}';
    }
}
