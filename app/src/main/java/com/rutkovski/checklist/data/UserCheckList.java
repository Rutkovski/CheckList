package com.rutkovski.checklist.data;

import androidx.room.Entity;
import androidx.room.Ignore;

import java.util.Calendar;

@Entity(tableName = "userCheckList")
public class UserCheckList extends CheckListTemplate {
    private Integer status; //1 - добавлено, 2 - завершено, 3 - просмотр в шаблоне
    private int isExpired;//Просрочено
    private String objectOfVerification;//объект проверки
    private double executionResult; //результат проверки
    private Long addTime; // время добавления
    private long deadLine; // срок
    private long startTime;// время начала
    private long finishTime;// время окончания
    private long completeTime; // затраченное время

    public UserCheckList(int id, String name, String description, int status, int isExpired, String objectOfVerification, double executionResult, long addTime, long deadLine, long startTime, long finishTime, long completeTime) {
        super(id, name, description);
        this.status = status;
        this.isExpired = isExpired;
        this.objectOfVerification = objectOfVerification;
        this.executionResult = executionResult;
        this.addTime = addTime;
        this.deadLine = deadLine;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.completeTime = completeTime;
    }

    @Ignore
    public UserCheckList(CheckListTemplate checkListTemplate) {
        super(checkListTemplate.getName(), checkListTemplate.getDescription());
        this.status = 1;
        this.isExpired = 0;
        Calendar calendar = Calendar.getInstance();
        this.addTime = calendar.getTimeInMillis();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public int getIsExpired() {
        return isExpired;
    }

    public void setIsExpired(int isExpired) {
        this.isExpired = isExpired;
    }

    public String getObjectOfVerification() {
        return objectOfVerification;
    }

    public void setObjectOfVerification(String objectOfVerification) {
        this.objectOfVerification = objectOfVerification;
    }

    public double getExecutionResult() {
        return executionResult;
    }

    public void setExecutionResult(double executionResult) {
        this.executionResult = executionResult;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public long getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(long deadLine) {
        this.deadLine = deadLine;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

    public long getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(long completeTime) {
        this.completeTime = completeTime;
    }
}