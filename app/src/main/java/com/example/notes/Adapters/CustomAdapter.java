package com.example.notes.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.Activities.MainActivity;
import com.example.notes.Activities.UpdateNote;
import com.example.notes.Database.DatabaseHelper;
import com.example.notes.Models.HeadingAndDescriptionModel;
import com.example.notes.R;
import com.example.notes.databinding.CustomDialogBinding;
import com.example.notes.databinding.SampleNotesBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.viewHolder> implements Filterable {


    Context context;
    List<HeadingAndDescriptionModel> modelList;
    List<HeadingAndDescriptionModel> noteList;
    Button cancel, delete;
//    HeadingAndDescriptionModel GET_ID;
    public static int INT_GET_ID;

    public CustomAdapter(Context context, List<HeadingAndDescriptionModel> modelList) {
        this.context = context;
        this.modelList = modelList;
        this.noteList = new ArrayList<>(modelList);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_notes, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.viewHolder holder, int position) {
        HeadingAndDescriptionModel m = modelList.get(position);
        INT_GET_ID = m.getId();
        holder.binding.tvNotesHeading.setText(m.getHeading());
        holder.binding.tvNotesDes.setText(m.getDescription());
        holder.binding.tvNoteDate.setText(m.getTime());



        // for note update
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateNote.class);
                intent.putExtra("heading", holder.binding.tvNotesHeading.getText().toString());
                intent.putExtra("description", holder.binding.tvNotesDes.getText().toString());

                ((Activity) context).startActivityForResult(intent, 2);

            }
        });



        // for note delete
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog optionDialog = new AlertDialog.Builder(v.getRootView().getContext()).create();
                View dialogView = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.custom_dialog, null);
                optionDialog.setView(dialogView);
                optionDialog.setCancelable(true);
                optionDialog.show();

                delete = dialogView.findViewById(R.id.btnDelete);
                cancel = dialogView.findViewById(R.id.btnCancel);


                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseHelper delete = new DatabaseHelper(context.getApplicationContext());
                        HeadingAndDescriptionModel hdm = new HeadingAndDescriptionModel(INT_GET_ID, m.getHeading(), m.getDescription());
                        delete.deleteNote(hdm);

                        if(context instanceof MainActivity){
                            MainActivity activity = (MainActivity)context;
                            activity.loadNotes();
                        }
                        optionDialog.dismiss();

                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionDialog.dismiss();
                    }
                });

                return true;
            }
        });
    }






    @Override
    public int getItemCount() {
        return modelList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<HeadingAndDescriptionModel> filterList = new ArrayList<>();

            if (constraint.toString().isEmpty()) {
                filterList.addAll(noteList);
            }
            else {
                for (HeadingAndDescriptionModel note : noteList) {
                    if (note.getHeading().toLowerCase().contains(constraint.toString().toLowerCase())
                        || note.getDescription().toLowerCase().contains(constraint.toString().toLowerCase())
                        || note.getTime().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filterList.add(note);
                    }
                }
            }
//            for (HeadingAndDescriptionModel note : noteList) {
//                if (note.getHeading().toLowerCase().contains(constraint.toString().toLowerCase())
//                    || note.getDescription().toLowerCase().contains(constraint.toString().toLowerCase())
//                    || note.getTime().toLowerCase().contains(constraint.toString().toLowerCase())) {
//                    filterList.add(note);
//                }
//            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            modelList.clear();
            modelList.addAll((Collection<? extends HeadingAndDescriptionModel>) results.values);
            notifyDataSetChanged();
        }
    };


    public class viewHolder extends RecyclerView.ViewHolder {

        SampleNotesBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SampleNotesBinding.bind(itemView);
        }
    }


}
