package com.rutkovski.checklist.data;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {CheckListTemplate.class, CheckPointTemplate.class, UserCheckList.class, UserCheckPoint.class}, version = 12, exportSchema = false)
public abstract class CheckListDataBase extends RoomDatabase  {
    private static final String DB_NAME = "checkList.db";
    private static CheckListDataBase dataBase;
    private static final Object LOCK = new Object();

    public static CheckListDataBase getInstance(Context context){
        synchronized (LOCK) {
            if (dataBase==null){
                dataBase = Room
                        .databaseBuilder(context, CheckListDataBase.class, DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return dataBase;
    }

    public abstract CheckListDao checkListDao();

}
