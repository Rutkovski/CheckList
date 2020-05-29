package com.rutkovski.checklist;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.rutkovski.checklist.data.MainViewModel;
import com.rutkovski.checklist.data.UserCheckPoint;

public class PointAddNoteActivity extends AppCompatActivity {
    UserCheckPoint userCheckPoint;
    private int id;
    private MainViewModel mainViewModel;
    private EditText editTextAddNote;
    private TextView textView;

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
        setContentView(R.layout.activity_point_add_note);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        textView = findViewById(R.id.textViewAddNote);
        editTextAddNote = findViewById(R.id.editTextAddNote);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        Intent intent = getIntent();
        id = intent.getIntExtra("pointId", -1);
        int status = intent.getIntExtra("status", -1);
        userCheckPoint = mainViewModel.getUserCheckPointByID(id);

        if (userCheckPoint.getNote()!= null&&status!=2) {
            editTextAddNote.setText(userCheckPoint.getNote());
        }else {
            editTextAddNote.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.VISIBLE);
            textView.setText(userCheckPoint.getNote());
        }

    }


    @Override
    public void onBackPressed() {
        String editText = editTextAddNote.getText().toString();
        userCheckPoint.setNote(editText);
        mainViewModel.updateUserCheckPoint(userCheckPoint);
        super.onBackPressed();
    }
}
