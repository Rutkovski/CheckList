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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rutkovski.checklist.AddCheckListActivity;
import com.rutkovski.checklist.CheckListTemplateActivity;
import com.rutkovski.checklist.MainActivity;
import com.rutkovski.checklist.R;
import com.rutkovski.checklist.adapters.ChecklistAdapter;
import com.rutkovski.checklist.data.CheckListTemplate;
import com.rutkovski.checklist.data.CheckPointTemplate;
import com.rutkovski.checklist.data.MainViewModel;
import com.rutkovski.checklist.data.UserCheckList;
import com.rutkovski.checklist.data.UserCheckPoint;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TemplateFragment extends Fragment {

    private RecyclerView recyclerViewToFulfillment;
    private ChecklistAdapter checklistAdapter;
    private MainViewModel mainViewModel;
    private FloatingActionButton floatingActionButton;

    public TemplateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_template, container, false);
        floatingActionButton = rootView.findViewById(R.id.floatingActionButtonNewCheckList);
        recyclerViewToFulfillment = rootView.findViewById(R.id.recyclerViewToFulfillment);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerViewToFulfillment.setLayoutManager(linearLayoutManager);
        checklistAdapter = new ChecklistAdapter(true);
        recyclerViewToFulfillment.setAdapter(checklistAdapter);
        getDataFromBD();
        setListeners();

       /* ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return super.getMovementFlags(recyclerView, viewHolder);
            }

            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerViewToFulfillment);*/

        return rootView;
    }

    private void getDataFromBD() {
        LiveData<List<CheckListTemplate>> checkListFromLD = mainViewModel.getCheckListsTemplate();
        checkListFromLD.observe(getViewLifecycleOwner(), new Observer<List<CheckListTemplate>>() {
            @Override
            public void onChanged(List<CheckListTemplate> checkListsFromBDTemplate) {
                if (checkListsFromBDTemplate != null) {
                    checklistAdapter.setCheckList(checkListsFromBDTemplate);
                }
            }
        });
    }


    public void setListeners() {

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddCheckListActivity.class);
                startActivity(intent);
            }
        });

        checklistAdapter.setOnCheckListClickListener(new ChecklistAdapter.OnCheckListClickListener() {

            @Override
            public void onCheckListClick(int position) {
                CheckListTemplate checkListTemplate = checklistAdapter.getCheckListTemplates().get(position);
                Intent intent = new Intent(getActivity(), CheckListTemplateActivity.class);
                intent.putExtra("id", checkListTemplate.getId());
                startActivity(intent);
            }

            @Override
            public void onCheckLongClick(MenuItem menuItem, int position) {
                CheckListTemplate checkListTemplate = checklistAdapter.getCheckListTemplates().get(position);
                switch (menuItem.getItemId()){
                    case (R.id.itemContextAdd):
                        addToTasks(checkListTemplate);
                        break;
                    case (R.id.itemContextCopy):
                        copyCheckListAndAllPoint(checkListTemplate);
                        break;
                    case(R.id.itemContextDelete):
                        deleteCheckListAndAllPoint(checkListTemplate);
                        break;
                }
            }
        });
    }



    private void addToTasks(final CheckListTemplate checkListTemplate){
        UserCheckList userCheckList = new UserCheckList(checkListTemplate);
        final int userCheckListId =(int)(long) mainViewModel.insertUserChecklistAndGetID(userCheckList);
        List <CheckPointTemplate> checkPointTemplates = mainViewModel.getCheckPointFromChecklistTemplateList(checkListTemplate.getId());
        if (checkPointTemplates != null) {
            List<UserCheckPoint> userCheckPoints = new ArrayList<>();
            for (CheckPointTemplate checkPointTemplate:checkPointTemplates) {
                userCheckPoints.add(new UserCheckPoint(userCheckListId,checkPointTemplate));
            }
            mainViewModel.insertUserCheckPointArray(userCheckPoints);
        }
        Toast.makeText(getContext(), "Чек-лист добавлен!", Toast.LENGTH_SHORT).show();
    }

    private void deleteCheckListAndAllPoint (CheckListTemplate checkListTemplate){
        mainViewModel.deleteChecklistTemplate(checkListTemplate);
        mainViewModel.deleteAllCheckPointTemplateByCheckId(checkListTemplate.getId());
        Toast.makeText(getContext(), "Удалено!", Toast.LENGTH_SHORT).show();
    }

    private void copyCheckListAndAllPoint(final CheckListTemplate checkListTemplate){
        int checkLisTemplateId =(int)(long) mainViewModel.insertChecklistTemplateAndGetID(new CheckListTemplate(checkListTemplate));
        List <CheckPointTemplate> checkPointTemplates = mainViewModel.getCheckPointFromChecklistTemplateList(checkListTemplate.getId());
        if (checkPointTemplates != null) {
            List <CheckPointTemplate> copyListPoint = new ArrayList<>();
            for (CheckPointTemplate checkPointTemplate:checkPointTemplates) {
                copyListPoint.add(new CheckPointTemplate(checkPointTemplate, checkLisTemplateId));
            }
            mainViewModel.insertCheckPointTemplateArray(copyListPoint);
        }
        Toast.makeText(getContext(), "Создана копия!", Toast.LENGTH_SHORT).show();
    }



}
