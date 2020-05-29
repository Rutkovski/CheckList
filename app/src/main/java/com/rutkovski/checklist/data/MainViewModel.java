package com.rutkovski.checklist.data;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainViewModel extends AndroidViewModel {
    private static CheckListDataBase checkListDataBase;
    private LiveData<List<CheckListTemplate>> checkListsTemplate;
    private LiveData<List<CheckPointTemplate>> checkPointsTemplate;
    private LiveData<List<UserCheckList>> userCheckLists;
    private LiveData<List<UserCheckPoint>> userCheckPoints;


    public MainViewModel(@NonNull Application application) {
        super(application);
        checkListDataBase = CheckListDataBase.getInstance(getApplication());
        checkListsTemplate = checkListDataBase.checkListDao().getAllCheckList();
        checkPointsTemplate = checkListDataBase.checkListDao().getAllCheckPoint();
        userCheckLists = checkListDataBase.checkListDao().getAllUserCheckList();
        userCheckPoints = checkListDataBase.checkListDao().getAllUserCheckPoint();
    }

    // Получение данных
    // Геттеры
    public LiveData<List<UserCheckList>> getUserCheckLists() {
        return userCheckLists;
    }
    public LiveData<List<UserCheckPoint>> getUserCheckPoints() {
        return userCheckPoints;
    }
    public LiveData<List<CheckListTemplate>> getCheckListsTemplate() {
        return checkListsTemplate;
    }
    public LiveData<List<CheckPointTemplate>> getCheckPointsTemplate() {
        return checkPointsTemplate;
    }

    //Из БД
    public LiveData<List<CheckPointTemplate>> getCheckPointFromChecklistTemplate(int checkListId){
        return checkListDataBase.checkListDao().getCheckPointFromCheckList(checkListId);
    }
    public LiveData<List<UserCheckPoint>> getCheckPointFromUserChecklist (int checkListId){
        return checkListDataBase.checkListDao().getUserCheckPointFromCheckList(checkListId);
    }

    public List <CheckPointTemplate> getCheckPointFromChecklistTemplateList (int id){
        try {
            return new GetCheckPointTemplateListTask().execute(id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List <UserCheckPoint> getUserCheckPointFromThisList (int id){
        try {
            return new GetUserCheckPointTemplateListTask().execute(id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }







    public CheckListTemplate getChecklistByID(int id){
        try {
          return new GetChecklistTask().execute(id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserCheckList getUserChecklistByID(int id){
        try {
            return new GetUserChecklistTask().execute(id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    public UserCheckPoint getUserCheckPointByID(int id){
        try {
            return new GetUserCheckPointByIdTask().execute(id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }




    // Обновление данных
    public void updateChecklist(CheckListTemplate checkListTemplate){
        new UpdateChecklistTask().execute(checkListTemplate);
    }
    public void updateUserChecklist(UserCheckList userCheckList){
        new UpdateUserChecklistTask().execute(userCheckList);
    }

    public void updateUserCheckPoint (UserCheckPoint userCheckPoint){
        new UpdateUserCheckPointTask().execute(userCheckPoint);
    }


    // Вставки
    public void insertChecklist(CheckListTemplate checkListTemplate){
        new InsertChecklistTask().execute(checkListTemplate);
    }

    public void insertCheckPoint(CheckPointTemplate checkPointTemplate){
        new InsertCheckPointTask().execute(checkPointTemplate);
    }
    public void insertCheckPointTemplateArray(List <CheckPointTemplate> list){
       new InsertCheckPointTemplateArrayTask().execute(list);
    }
    public void insertUserCheckPointArray(List <UserCheckPoint> list){
        new InsertUserCheckPointArrayTask().execute(list);
    }
    public Long insertChecklistTemplateAndGetID(CheckListTemplate checkListTemplate){
        try {
            return new InsertChecklistTemplateAndGetIdTask().execute(checkListTemplate).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Long insertUserChecklistAndGetID (UserCheckList userCheckList){
        try {
            return new InsertUserChecklistAndGetIdTask().execute(userCheckList).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }return null;
    }

    // Удаления

    public void deleteChecklistTemplate(CheckListTemplate checkListTemplate){
        new DeleteChecklistTemplateTask().execute(checkListTemplate);
    }
    public void deleteUserChecklist(UserCheckList userCheckList){
        new DeleteUserChecklistTask().execute(userCheckList);
    }
    public void deleteCheckPoint(CheckPointTemplate checkPointTemplate){
        new DeleteCheckPointTask().execute(checkPointTemplate);
    }
    public void deleteAllCheckPointTemplateByCheckId(int checkListId){
        new DeleteCheckPointTemplateByCheckListIdTask().execute(checkListId);
    }
    public void deleteAllUserCheckPointByCheckListId(int checkListId){
        new DeleteAllUserCheckPointCheckListIdTask().execute(checkListId);
    }


    // Классы AsyncTask


// Получение данных
    private static class GetChecklistTask extends AsyncTask <Integer, Void, CheckListTemplate> {
        @Override
        protected CheckListTemplate doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0){
               return checkListDataBase.checkListDao().getChecklistById(integers[0]);
            }
            return null;
        }
    }

    private static class GetCheckPointTemplateListTask extends AsyncTask <Integer, Void, List<CheckPointTemplate> > {
        @Override
        protected List <CheckPointTemplate> doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0){
                return checkListDataBase.checkListDao().getAllThisPoint(integers[0]);
            }
            return null;
        }
    }

    private static class GetUserCheckPointTemplateListTask extends AsyncTask <Integer, Void, List<UserCheckPoint> > {
        @Override
        protected List <UserCheckPoint> doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0){
                return checkListDataBase.checkListDao().getAllUserPoint(integers[0]);
            }
            return null;
        }
    }




    private static class GetUserChecklistTask extends AsyncTask <Integer, Void, UserCheckList> {
        @Override
        protected UserCheckList doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0){
                return checkListDataBase.checkListDao().getUserChecklistById(integers[0]);
            }
            return null;
        }
    }
    private static class GetUserCheckPointByIdTask extends AsyncTask <Integer, Void, UserCheckPoint> {
        @Override
        protected UserCheckPoint doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0){
                return checkListDataBase.checkListDao().getUserCheckPointById(integers[0]);
            }
            return null;
        }
    }



    private static class InsertChecklistTask extends AsyncTask <CheckListTemplate, Void, Void> {
        @Override
        protected Void doInBackground(CheckListTemplate... checkListTemplates) {
            if (checkListTemplates != null && checkListTemplates.length > 0){
                 checkListDataBase.checkListDao().insertCheckList(checkListTemplates[0]);
            }
            return null;
        }
    }

    private static class UpdateChecklistTask extends AsyncTask <CheckListTemplate, Void, Void> {
        @Override
        protected Void doInBackground(CheckListTemplate... checkListTemplates) {
            if (checkListTemplates != null && checkListTemplates.length > 0){
                checkListDataBase.checkListDao().updateCheckListTemplate(checkListTemplates[0]);
            }
            return null;
        }
    }

    private static class UpdateUserChecklistTask extends AsyncTask <UserCheckList, Void, Void> {
        @Override
        protected Void doInBackground(UserCheckList... userCheckLists) {
            if (userCheckLists != null && userCheckLists.length > 0){
                checkListDataBase.checkListDao().updateUserCheckList(userCheckLists[0]);
            }
            return null;
        }
    }

    private static class UpdateUserCheckPointTask extends AsyncTask <UserCheckPoint, Void, Void> {
        @Override
        protected Void doInBackground(UserCheckPoint... userCheckPoints) {
            if (userCheckPoints != null && userCheckPoints.length > 0){
                checkListDataBase.checkListDao().updateUserCheckPoint(userCheckPoints[0]);
            }
            return null;
        }
    }

    private static class InsertChecklistTemplateAndGetIdTask extends AsyncTask <CheckListTemplate, Void, Long> {
        @Override
        protected Long doInBackground(CheckListTemplate... checkListTemplates) {
            if (checkListTemplates !=null && checkListTemplates.length>0){
                return checkListDataBase.checkListDao().insertCheckListTemplateAndGetID(checkListTemplates[0]);
            }
            return null;
        }
    }


    private static class InsertUserChecklistAndGetIdTask extends AsyncTask <UserCheckList, Void, Long> {
        @Override
        protected Long doInBackground(UserCheckList... userCheckLists) {
            if (userCheckLists !=null && userCheckLists.length>0){
                return checkListDataBase.checkListDao().insertUserCheckListAndGetID(userCheckLists[0]);
            }
            return null;
        }
    }


    private static class InsertCheckPointTask extends AsyncTask <CheckPointTemplate, Void, Void> {
        @Override
        protected Void doInBackground(CheckPointTemplate... checkPointTemplates) {
            if (checkPointTemplates != null && checkPointTemplates.length > 0){
                checkListDataBase.checkListDao().insertCheckPoint(checkPointTemplates[0]);
            }
            return null;
        }
    }

    private static class InsertCheckPointTemplateArrayTask extends AsyncTask <List<CheckPointTemplate>, Void, Void> {
        @Override
        protected Void doInBackground(List<CheckPointTemplate>... arrayLists) {
            List<CheckPointTemplate> checkPointTemplates = arrayLists[0];
            checkListDataBase.checkListDao().insertCheckPointTemplateArray(checkPointTemplates);
            return null;
        }
    }


    private static class InsertUserCheckPointArrayTask extends AsyncTask <List<UserCheckPoint>, Void, Void> {
        @Override
        protected Void doInBackground(List<UserCheckPoint>... lists) {
            List<UserCheckPoint> userCheckPoints = lists[0];
            checkListDataBase.checkListDao().insertUserCheckPointArray(userCheckPoints);
            return null;
        }
    }

    private static class DeleteChecklistTemplateTask extends AsyncTask <CheckListTemplate, Void, Void> {
        @Override
        protected Void doInBackground(CheckListTemplate... checkListTemplates) {
            if (checkListTemplates != null && checkListTemplates.length > 0){
                checkListDataBase.checkListDao().deleteCheckListTemplate(checkListTemplates[0]);
            }
            return null;
        }
    }

    private static class DeleteUserChecklistTask extends AsyncTask <UserCheckList, Void, Void> {
        @Override
        protected Void doInBackground(UserCheckList... userCheckLists) {
            if (userCheckLists != null && userCheckLists.length > 0){
                checkListDataBase.checkListDao().deleteUserCheckList(userCheckLists[0]);
            }
            return null;
        }
    }

    private static class DeleteCheckPointTask extends AsyncTask <CheckPointTemplate, Void, Void> {
        @Override
        protected Void doInBackground(CheckPointTemplate... checkPointTemplates) {
            if (checkPointTemplates != null && checkPointTemplates.length > 0){
                checkListDataBase.checkListDao().deleteCheckPointTemplate(checkPointTemplates[0]);
            }
            return null;
        }
    }

    private static class DeleteCheckPointTemplateByCheckListIdTask extends AsyncTask <Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0){
                checkListDataBase.checkListDao().deleteAllCheckPointTemplateById(integers[0]);
            }
            return null;
        }
    }

    private static class DeleteAllUserCheckPointCheckListIdTask extends AsyncTask <Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0){
                checkListDataBase.checkListDao().deleteAllUserCheckPointByCheckListId(integers[0]);
            }
            return null;
        }
    }


}
