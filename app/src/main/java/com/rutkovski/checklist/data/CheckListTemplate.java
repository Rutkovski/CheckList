package com.rutkovski.checklist.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "checkListTemplate")
public class CheckListTemplate {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String description;


    public CheckListTemplate(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Ignore
    public CheckListTemplate(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Ignore
    public CheckListTemplate(String name) {
        this.name = name;
    }

    @Ignore
    public CheckListTemplate(CheckListTemplate checkListTemplate) {
        this.name = checkListTemplate.getName();
        this.description = checkListTemplate.getDescription();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
