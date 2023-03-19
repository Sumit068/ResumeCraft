package com.example.resumecraft;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentProject extends Fragment {

    RecyclerView project_list;
    AdapterProject adapter;
    Button project_add , project_save;
    Profile resume_profile;
    ArrayList<Profile.ProjectDetails> projects = new ArrayList<>();

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mUser = mAuth.getCurrentUser();
    DatabaseReference mData=FirebaseDatabase.getInstance().getReference("users/"+mUser.getUid()+"/profiles/");
    String resume_id ;
    ProgressDialog progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_project, container, false);

        resume_id = ((UserInputData)getActivity()).resume_id;

        project_save = view.findViewById(R.id.project_save);
        project_add =view.findViewById(R.id.project_add);
        project_list = view.findViewById(R.id.project_list);

        mData.child(resume_id).addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progress = new ProgressDialog(getContext());
                progress.setTitle("Progress");
                progress.setMessage("Data is fetching...");
                progress.setCancelable(false);
                progress.show();
                resume_profile = snapshot.getValue(Profile.class);
                if(resume_profile != null){
                    if(resume_profile.project!=null){
                        projects=resume_profile.project;
                    }
                }
                else{
                    resume_profile = new Profile();
                }
                project_list.setLayoutManager(new LinearLayoutManager(view.getContext()));
                adapter = new AdapterProject(getContext(), projects);
                project_list.setAdapter(adapter);
                progress.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });

        project_add.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){
               AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
               LayoutInflater inflater = getLayoutInflater();
               View dialog_view = inflater.inflate(R.layout.dialog_project , null);
               dialog.setView(dialog_view);

               dialog.setNeutralButton("Cancel" , new DialogInterface.OnClickListener(){
                   @Override
                   public void onClick(DialogInterface dialogInterface , int i){
                       Toast.makeText(getContext(),"Cancelled", LENGTH_SHORT).show();
                   }
               });

               dialog.setPositiveButton("Submit", new DialogInterface.OnClickListener(){
                   @Override
                   public void onClick(DialogInterface dialogInterface , int i){
                       String project = ((EditText)dialog_view.findViewById(R.id.input_project)).getText().toString();
                       String description = ((EditText)dialog_view.findViewById(R.id.input_description)).getText().toString();
                       if(project.isEmpty() || description.isEmpty()){
                           Toast.makeText(getContext(),"fill all inputs",LENGTH_SHORT).show();
                       }
                       else{
                           projects.add(new Profile.ProjectDetails(project , description));
                           adapter.notifyItemInserted(projects.size()-1);
                           project_list.scrollToPosition(projects.size()-1);
                       }
                   }
               });
               dialog.create();
               dialog.show();
           }
        });

        project_save.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                progress.setMessage("uploading...");
                progress.setCancelable(false);
                progress.show();
                resume_profile.project =projects;
                mData.child(resume_id).setValue(resume_profile);
                progress.dismiss();
                Toast.makeText(getContext() , "Your data is saved , you can go back" , LENGTH_LONG).show();
            }
        });

        return view;
    }
}