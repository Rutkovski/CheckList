package com.rutkovski.checklist;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rutkovski.checklist.adapters.CheckPointsAdapter;
import com.rutkovski.checklist.data.CheckListTemplate;
import com.rutkovski.checklist.data.CheckPointTemplate;
import com.rutkovski.checklist.data.MainViewModel;

import java.util.ArrayList;

public class AddCheckListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewsAddPoint;
    private CheckPointsAdapter checkPointsAdapter;
    private ArrayList<CheckPointTemplate> checkPointTemplates = new ArrayList<>();
    private EditText editTextDescriptionOfPoint;
    private EditText editTextTitleOfCheck;
    private MainViewModel  viewModel;
    private int listID;
    private CheckListTemplate checkListTemplate;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

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
        setContentView(R.layout.activity_add_chek_list);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        }

        recyclerViewsAddPoint = findViewById(R.id.recyclerviewAddPoint);
        editTextDescriptionOfPoint = findViewById(R.id.editTextPointDescription);
        editTextTitleOfCheck = findViewById(R.id.editTextTitleCheck);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewsAddPoint.setLayoutManager(linearLayoutManager);
        checkPointsAdapter = new CheckPointsAdapter();
        checkPointsAdapter.setCheckPoint(checkPointTemplates);
        recyclerViewsAddPoint.setAdapter(checkPointsAdapter);
        listID = viewModel.insertChecklistTemplateAndGetID(new CheckListTemplate("")).intValue();
        checkListTemplate = viewModel.getChecklistByID(listID);
    }


    public void onClickAddPoint(View view) {
        String descriptionOfCheckPoint = editTextDescriptionOfPoint.getText().toString();
        if (descriptionOfCheckPoint.length()>0) {
            checkPointTemplates.add(new CheckPointTemplate(listID, descriptionOfCheckPoint));
            editTextDescriptionOfPoint.getText().clear();
            checkPointsAdapter.notifyDataSetChanged();
            recyclerViewsAddPoint.smoothScrollToPosition(recyclerViewsAddPoint.getAdapter().getItemCount() - 1);
        }
    }

    @Override
    public void onBackPressed() {
        String titleOfCheck = editTextTitleOfCheck.getText().toString();

        if (titleOfCheck.length()!=0) {
            checkListTemplate.setName(titleOfCheck);
            viewModel.updateChecklist(checkListTemplate);
            viewModel.insertCheckPointTemplateArray(checkPointTemplates);
            super.onBackPressed();
        } else if(titleOfCheck.length()==0&&checkPointTemplates.size()>0 ) {
            Toast.makeText(this, "Заполните название!", Toast.LENGTH_SHORT).show();
        }else {
            super.onBackPressed();

        }

    }
}
