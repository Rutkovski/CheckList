package com.rutkovski.checklist;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rutkovski.checklist.adapters.CheckPointsAdapter;
import com.rutkovski.checklist.data.CheckListTemplate;
import com.rutkovski.checklist.data.CheckPointTemplate;
import com.rutkovski.checklist.data.MainViewModel;
import com.rutkovski.checklist.data.UserCheckList;

import java.util.List;

public class CheckListTemplateActivity extends AppCompatActivity {
    private RecyclerView recyclerViewCheckPoints;
    private CheckPointsAdapter checkPointsAdapter;
    private MainViewModel mainViewModel;
    private int idFromCheckList;
    private CheckListTemplate checkListTemplate;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list_template);


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            idFromCheckList = intent.getIntExtra("id", 0);
        } else {
            finish();
        }

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        checkListTemplate =  mainViewModel.getChecklistByID(idFromCheckList);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(checkListTemplate.getName());
        }


        recyclerViewCheckPoints = findViewById(R.id.recyclerViewCheckPointsTemplate);

        getDataFromDBTemplate();
        checkPointsAdapter = new CheckPointsAdapter();
        recyclerViewCheckPoints.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCheckPoints.setAdapter(checkPointsAdapter);
        setListeners();


    }


    private void getDataFromDBTemplate() {
        LiveData<List<CheckPointTemplate>> checkPointLiveData = mainViewModel.getCheckPointFromChecklistTemplate(idFromCheckList);
        checkPointLiveData.observe(this, new Observer<List<CheckPointTemplate>>() {
            @Override
            public void onChanged(List<CheckPointTemplate> checkPointTemplates) {
                if (checkPointTemplates != null) {
                    checkPointsAdapter.setCheckPoint(checkPointTemplates);

                }
            }
        });
    }
    public void setListeners() {


    }


}

