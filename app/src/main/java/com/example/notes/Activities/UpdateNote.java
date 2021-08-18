package com.example.notes.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.notes.databinding.ActivityUpdateNoteBinding;

public class UpdateNote extends AppCompatActivity {

    ActivityUpdateNoteBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();


        String heading = getIntent().getStringExtra("heading");
        String description = getIntent().getStringExtra("description");


        binding.notesHeadingUpdate.setText(heading);
        binding.notesDescriptionUpdate.setText(description);



        binding.updateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateNote.this, MainActivity.class);
                intent.putExtra("up_heading", binding.notesHeadingUpdate.getText().toString());
                intent.putExtra("up_description", binding.notesDescriptionUpdate.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
}