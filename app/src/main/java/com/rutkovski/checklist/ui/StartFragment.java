package com.rutkovski.checklist.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;
import androidx.recyclerview.widget.SortedListAdapterCallback;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rutkovski.checklist.CheckListDiagnosticActivity;
import com.rutkovski.checklist.R;
import com.rutkovski.checklist.adapters.ChecklistAdapter;
import com.rutkovski.checklist.data.CheckListTemplate;
import com.rutkovski.checklist.data.CheckPointTemplate;
import com.rutkovski.checklist.data.MainViewModel;
import com.rutkovski.checklist.data.UserCheckList;
import com.rutkovski.checklist.data.UserCheckPoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment {
    private RecyclerView recyclerViewMyCheckList;
    private ChecklistAdapter checklistAdapter;
    private MainViewModel mainViewModel;



    public StartFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_start, container, false);
        recyclerViewMyCheckList =rootView.findViewById(R.id.recyclerViewMyCheckList);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getActivity());
        recyclerViewMyCheckList.setLayoutManager(linearLayoutManager);

        checklistAdapter = new ChecklistAdapter(false);
        recyclerViewMyCheckList.setAdapter(checklistAdapter);


        getDataFromBD();
        setListeners();

        /*ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                final int fromPos = viewHolder.getAdapterPosition();
                final int toPos = target.getAdapterPosition();
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int adapterPosition = viewHolder.getAdapterPosition();
                CheckListTemplate checkListTemplate = checklistAdapter.getCheckListTemplates().get(adapterPosition);
                UserCheckList userCheckList = (UserCheckList) (checkListTemplate);
                mainViewModel.deleteUserChecklist(userCheckList);
                mainViewModel.deleteAllUserCheckPointByCheckListId(checkListTemplate.getId());

            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerViewMyCheckList);*/
        return rootView;

    }

    private void getDataFromBD() {
        LiveData<List<UserCheckList>> checkListFromLD = mainViewModel.getUserCheckLists();
        checkListFromLD.observe(getViewLifecycleOwner(), new Observer<List<UserCheckList>>() {
            @Override
            public void onChanged(List<UserCheckList> userCheckLists) {

               Collections.sort(userCheckLists, new Comparator<UserCheckList>() {
                    @Override
                    public int compare(UserCheckList userCheckList, UserCheckList t1) {
                        return t1.getStatus().compareTo(userCheckList.getStatus());
                    }
                });
               Collections.reverse(userCheckLists);
                checklistAdapter.setUserCheckList(userCheckLists);
            }
        });
    }


    public void setListeners() {
        checklistAdapter.setOnCheckListClickListener(new ChecklistAdapter.OnCheckListClickListener() {
            @Override
            public void onCheckListClick(int position) {
                CheckListTemplate checkListTemplate = checklistAdapter.getCheckListTemplates().get(position);
                Intent intent = new Intent(getActivity(), CheckListDiagnosticActivity.class);
                intent.putExtra("idFromMain", checkListTemplate.getId());
                startActivity(intent);
            }
            @Override
            public void onCheckLongClick(MenuItem  menuItem, int position) {
                UserCheckList userCheckList = (UserCheckList) checklistAdapter.getCheckListTemplates().get(position);
                switch (menuItem.getItemId()){
                    case (R.id.itemContextCopy):
                        copyCheckListAndAllPoint(userCheckList);
                        break;
                    case(R.id.itemContextDelete):
                        deleteCheckListAndAllPoint(userCheckList);
                        break;
                }
            }
        });
    }



    private void deleteCheckListAndAllPoint (UserCheckList userCheckList){
        mainViewModel.deleteUserChecklist(userCheckList);
        mainViewModel.deleteAllUserCheckPointByCheckListId(userCheckList.getId());
        Toast.makeText(getContext(), "Удалено!", Toast.LENGTH_SHORT).show();
    }

    private void copyCheckListAndAllPoint(UserCheckList userCheckList){
        int userCheckListID =(int)(long) mainViewModel.insertUserChecklistAndGetID(new UserCheckList(userCheckList));
        List <UserCheckPoint> userCheckPoints = mainViewModel.getUserCheckPointFromThisList(userCheckList.getId());
        if (userCheckPoints != null) {
            List <UserCheckPoint> copyListPoint = new ArrayList<>();
            for (UserCheckPoint userCheckPoint:userCheckPoints) {
                copyListPoint.add(new UserCheckPoint(userCheckPoint, userCheckListID));
            }
            mainViewModel.insertUserCheckPointArray(copyListPoint);
        }
        Toast.makeText(getContext(), "Создана копия!", Toast.LENGTH_SHORT).show();
    }


}
