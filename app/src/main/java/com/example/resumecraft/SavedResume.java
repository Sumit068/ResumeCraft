package com.example.resumecraft;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SavedResume extends AppCompatActivity {

    ArrayList<Profile> profiles = new ArrayList<>();
    RecyclerView saved_resume_list;
    SavedResumeAdapter adapter;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mUser = mAuth.getCurrentUser();
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference("users/"+mUser.getUid());
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_resume);

        saved_resume_list = findViewById(R.id.saved_resume_list);
        saved_resume_list.setLayoutManager(new LinearLayoutManager(SavedResume.this));
        progress = new ProgressDialog(SavedResume.this);
        progress.setTitle("Loading...");
        progress.setCancelable(false);
        progress.show();
        mData.child("profiles").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot){
                for(DataSnapshot data : snapshot.getChildren()){
                    profiles.add(data.getValue(Profile.class));
                }
                adapter = new SavedResumeAdapter(SavedResume.this , profiles);
                saved_resume_list.setAdapter(adapter);
                progress.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });
    }
}