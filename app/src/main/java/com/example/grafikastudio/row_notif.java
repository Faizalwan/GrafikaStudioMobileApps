package com.example.grafikastudio;

public class row_notif {
    String title,text;
    public row_notif(){
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public row_notif(String title, String text) {
        this.title = title;
        this.text = text;
    }
}
