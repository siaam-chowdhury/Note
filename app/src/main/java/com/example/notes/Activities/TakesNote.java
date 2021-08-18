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

        binding.okayBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.notesHeading.getText().toString().equals("")) {
                    Toast.makeText(TakesNote.this, "Heading must be declare", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent();

                    intent.putExtra("heading", binding.notesHeading.getText().toString());
                    intent.putExtra("description", binding.notesDescription.getText().toString());

                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });






    }
}