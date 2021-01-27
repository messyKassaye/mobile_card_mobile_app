package com.example.foragentss.auth.models;

public class DownloadCardData {
    private String data;
    private String update_type="downloaded";
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUpdate_type() {
        return update_type;
    }

    public void setUpdate_type(String update_type) {
        this.update_type = update_type;
    }
}
