package com.saeefmd.noteapphellotask.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String description;
    private String date;
    private String month;
    private int priority;


    public Note(String title, String description, String date, String month, int priority) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.month = month;
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getMonth() {
        return month;
    }

    public int getPriority() {
        return priority;
    }
}
