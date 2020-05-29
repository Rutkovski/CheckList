package com.rutkovski.checklist.data;


import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "userCheckPoint")
public class UserCheckPoint extends CheckPointTemplate {
    private String note;

    public UserCheckPoint(int id, int checkListId, String description, int result, String note) {
        super(id, checkListId, description, result);
        this.note = note;
    }

    @Ignore
    public UserCheckPoint(int checkListId, CheckPointTemplate checkPointTemplate) {
        super(checkListId,checkPointTemplate.getDescription());
    }
    @Ignore
    public UserCheckPoint(CheckPointTemplate checkPointTemplate) {
        super(checkPointTemplate.getId(), checkPointTemplate.getCheckListId(), checkPointTemplate.getDescription(), checkPointTemplate.getResult());
    }
    @Ignore
    public UserCheckPoint(UserCheckPoint userCheckPoint, int checkListId) {
        super(checkListId, userCheckPoint.getDescription(), userCheckPoint.getResult());
        this.note = userCheckPoint.note;
    }



    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
