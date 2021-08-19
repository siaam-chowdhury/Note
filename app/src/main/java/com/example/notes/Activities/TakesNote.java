package com.example.notes.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import com.example.notes.Database.DatabaseHelper;
import com.example.notes.R;
import com.example.notes.databinding.ActivityTakesNoteBinding;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class TakesNote extends AppCompatActivity {

    ActivityTakesNoteBinding binding;
    DatabaseHelper addNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTakesNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getSupportActionBar().hide();

        binding.okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.noteTitle.getText().toString().equals("")) {
                    Toast.makeText(TakesNote.this, "Title must be declare", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent();
                    intent.putExtra("heading", binding.noteTitle.getText().toString());
                    intent.putExtra("description", binding.noteDescription.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });



    }















}