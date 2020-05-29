package com.rutkovski.checklist.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CheckListDao {


//Получение данных
    @Query("SELECT * FROM CheckPointTemplate")
    LiveData<List<CheckPointTemplate>> getAllCheckPoint();




    @Query("SELECT * FROM CheckPointTemplate WHERE checkListId == :checkListId")

    LiveData<List<CheckPointTemplate>> getCheckPointFromCheckList(int checkListId);

    @Query("SELECT * FROM userCheckPoint WHERE checkListId == :checkListId")
    LiveData<List<UserCheckPoint>> getUserCheckPointFromCheckList(int checkListId);

    @Query("SELECT * FROM CheckPointTemplate WHERE checkListId == :checkListId")
    List <CheckPointTemplate> getAllThisPoint(int checkListId);

    @Query("SELECT * FROM userCheckPoint WHERE checkListId == :checkListId")
    List <UserCheckPoint> getAllUserPoint(int checkListId);





    @Query("SELECT * FROM CheckListTemplate")
    LiveData<List<CheckListTemplate>> getAllCheckList();

    @Query("SELECT * FROM userCheckList")
    LiveData<List<UserCheckList>> getAllUserCheckList();


    @Query("SELECT * FROM userCheckPoint")
    LiveData<List<UserCheckPoint>> getAllUserCheckPoint();




    @Query("SELECT * FROM CheckListTemplate WHERE id == :checkListId")
    CheckListTemplate getChecklistById(int checkListId);

    @Query("SELECT * FROM userCheckList WHERE id == :id")
    UserCheckList getUserChecklistById(int id);
    @Query("SELECT * FROM userCheckPoint WHERE id == :id")
    UserCheckPoint getUserCheckPointById(int id);


    // Вставка
    @Insert
    void insertCheckPoint(CheckPointTemplate checkPointTemplate);
    @Insert
    void insertCheckPointTemplateArray(List<CheckPointTemplate> checkPointTemplates);
    @Insert
    void insertUserCheckPointArray(List<UserCheckPoint> userCheckPoints);
    @Insert
    void insertCheckList(CheckListTemplate checkListTemplate);
    @Insert
    long insertUserCheckListAndGetID(UserCheckList userCheckList);
    @Insert
    long insertCheckListTemplateAndGetID(CheckListTemplate checkListTemplate);

// Обновления
    @Update
    void updateCheckListTemplate(CheckListTemplate checkListTemplate);
    @Update
    void updateUserCheckList(UserCheckList userCheckList);
    @Update
    void updateUserCheckPoint(UserCheckPoint userCheckPoint);


// Удаление
    @Delete
    void deleteCheckListTemplate(CheckListTemplate checkListTemplate);
    @Delete
    void deleteUserCheckList(UserCheckList userCheckList);
    @Delete
    void deleteCheckPointTemplate(CheckPointTemplate checkPointTemplate);

    @Query("DELETE FROM CheckListTemplate")
    void deleteAllCheckList();
    @Query("DELETE FROM CheckPointTemplate")
    void deleteAllCheckPoint();
    @Query("DELETE FROM CheckPointTemplate WHERE checkListId == :checkListId")
    void deleteAllCheckPointTemplateById(int checkListId);
    @Query("DELETE FROM userCheckPoint WHERE checkListId == :checkListId")
    void deleteAllUserCheckPointByCheckListId(int checkListId);

}
