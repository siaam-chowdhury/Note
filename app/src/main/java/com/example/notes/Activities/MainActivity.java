package com.example.notes.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.notes.Adapters.CustomAdapter;
import com.example.notes.Database.DatabaseHelper;
import com.example.notes.Models.HeadingAndDescriptionModel;
import com.example.notes.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        loadNotes();
        searchNotes();



        binding.addNotesBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TakesNote.class);
                startActivityForResult(intent, 1);
            }
        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        HeadingAndDescriptionModel f;
        String heading = "", description = "";
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                heading = data.getStringExtra("heading");
                description = data.getStringExtra("description");

                addNotes(heading, description);
                loadNotes();

            }
        }


        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                String updateHeading = data.getStringExtra("up_heading");
                String updateDescription = data.getStringExtra("up_description");
                addNotes(updateHeading, updateDescription);
                deleteNotes(heading, description);
                loadNotes();
            }
        }

    }



    private void deleteNotes(String heading, String description) {
        DatabaseHelper delete = new DatabaseHelper(this);
        HeadingAndDescriptionModel deleteNoteModel = null;
        deleteNoteModel= new HeadingAndDescriptionModel(CustomAdapter.INT_GET_ID, heading, description);
        delete.deleteNote(deleteNoteModel);
    }


    private void addNotes(String heading, String description) {
        DatabaseHelper add = new DatabaseHelper(this);
        HeadingAndDescriptionModel addNoteModel = new HeadingAndDescriptionModel(1, heading, description);
        add.addNote(addNoteModel);
    }


    public void loadNotes() {

        DatabaseHelper getAllNotes = new DatabaseHelper(this);
        List<HeadingAndDescriptionModel> getNotes = getAllNotes.getALl();

        customAdapter = new CustomAdapter(this, getNotes);
        binding.RV.setAdapter(customAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.RV.setLayoutManager(layoutManager);
    }


    private void searchNotes() {
        binding.svSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }




}