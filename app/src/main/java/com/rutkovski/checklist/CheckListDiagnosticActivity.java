package com.rutkovski.checklist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rutkovski.checklist.adapters.CheckPointsAdapter;
import com.rutkovski.checklist.data.MainViewModel;
import com.rutkovski.checklist.data.UserCheckList;
import com.rutkovski.checklist.data.UserCheckPoint;

import java.util.List;

public class CheckListDiagnosticActivity extends AppCompatActivity {
    public final String TAG = "test";
    private RecyclerView recyclerViewCheckPoints;
    private CheckPointsAdapter checkPointsAdapter;
    private MainViewModel mainViewModel;
    private int idFromCheckList;
    private UserCheckList userCheckList;
    private int status;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (userCheckList.getStatus()!=2){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.checklist_menu, menu);

        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.itemFinish:
                userCheckList.setStatus(2);
                mainViewModel.updateUserChecklist(userCheckList);
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list_diagnostic);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("idFromMain")) {
            idFromCheckList = intent.getIntExtra("idFromMain", 0);
        } else {
            finish();
        }
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        userCheckList = mainViewModel.getUserChecklistByID(idFromCheckList);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(userCheckList.getName());
        }

        recyclerViewCheckPoints = findViewById(R.id.recyclerViewCheckPoints);
        status = userCheckList.getStatus();
        checkPointsAdapter = new CheckPointsAdapter(status);
        userCheckList = mainViewModel.getUserChecklistByID(idFromCheckList);
        status = userCheckList.getStatus();
        setListeners();
        recyclerViewCheckPoints.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCheckPoints.setAdapter(checkPointsAdapter);
        getDataFromDBUser();
    }

    private void getDataFromDBUser() {
        List<UserCheckPoint> checkPoints = mainViewModel.getUserCheckPointFromThisList(idFromCheckList);
        if (checkPoints != null) {
            checkPointsAdapter.setUserCheckPoint(checkPoints);
        }
    }

    public void setListeners() {
        checkPointsAdapter.setOnOptionSelected(new CheckPointsAdapter.OnOptionSelected() {
            @Override
            public void onOptionSelected(int adapterPosition, int checkedButtonId, RadioGroup radioGroup) {

                int result = 0;
                UserCheckPoint userCheckPoint = (UserCheckPoint) (checkPointsAdapter.getCheckPointTemplates().get(adapterPosition));
                switch (checkedButtonId) {
                    case (R.id.radioButtonLike):
                        result = 1;
                        break;
                    case (R.id.radioButtonDislike):
                        result = 2;
                        break;
                    case (R.id.radioButtonSkip):
                        result = 3;
                        break;
                }
                if (userCheckPoint.getResult() != result) {
                    userCheckPoint.setResult(result);
                    Log.i(TAG, "onOptionSelected: записали в БД");
                    mainViewModel.updateUserCheckPoint(userCheckPoint);
                }
            }

            @Override
            public void onNoteClick(int adapterPosition) {
                Intent intent = new Intent(CheckListDiagnosticActivity.this, PointAddNoteActivity.class);
                int id = checkPointsAdapter.getCheckPointTemplates().get(adapterPosition).getId();
                intent.putExtra("pointId", id);
                intent.putExtra("status", userCheckList.getStatus());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getDataFromDBUser();
    }
}
