package com.adityaandroid.dailynotes.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;
 @Entity(tableName = "notes")
public class NotesEntity {
     @PrimaryKey(autoGenerate = true)
    private int id;
    private Date date;
    private String text;
    @Ignore
    public NotesEntity() {
    }

    public NotesEntity(int id, Date date, String text) {
        this.id = id;
        this.date = date;
        this.text = text;
    }
    @Ignore
    public NotesEntity(Date date, String text) {
        this.date = date;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "NotesEntity{" +
                "id=" + id +
                ", date=" + date +
                ", text='" + text + '\'' +
                '}';
    }
}
