package com.rutkovski.checklist.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "checkPointTemplate")
public class CheckPointTemplate {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int checkListId;
    private String description;
    private int result;


    public CheckPointTemplate(int id, int checkListId, String description, int result) {
        this.id = id;
        this.checkListId = checkListId;
        this.description = description;
        this.result = result;
    }


    @Ignore
    public CheckPointTemplate(int checkListId, String description) {
        this.checkListId = checkListId;
        this.description = description;
    }

    @Ignore
    public CheckPointTemplate(int checkListId, String description, int result) {
        this.checkListId = checkListId;
        this.description = description;
        this.result = result;
    }

    @Ignore
    public CheckPointTemplate(CheckPointTemplate checkPointTemplate, int checkListId) {
        this.checkListId = checkListId;
        this.description = checkPointTemplate.getDescription();
        this.result = checkPointTemplate.getResult();
    }


    public int getId() {
        return id;
    }

    public int getCheckListId() {
        return checkListId;
    }

    public void setCheckListId(int checkListId) {
        this.checkListId = checkListId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}