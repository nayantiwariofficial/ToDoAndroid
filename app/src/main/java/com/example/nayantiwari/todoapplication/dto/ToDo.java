package com.example.nayantiwari.todoapplication.dto;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.Date;

import static android.R.attr.description;

/**
 * Created by nayantiwari on 7/10/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ToDo implements Serializable {

    @JsonProperty("id")
    private long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("isChecked")
    private int isChecked;

    @JsonProperty("date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss ZZZ")
    private Date date;

    public ToDo() {
    }

    public ToDo(long id, String title, int isChecked, Date date) {
        this.id = id;
        this.title = title;
        this.isChecked = isChecked;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(int isChecked) {
        this.isChecked = isChecked;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ToDo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", isChecked='" + isChecked + '\'' +
                ", date=" + date +
                '}';
    }
}
