package com.example.resumecraft;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

public class FragmentExperience extends Fragment {
    Button experience_add , experience_save;
    RecyclerView experience_list;
    AdapterExperience adapter;
    Profile resume_profile;
    ArrayList<Profile.ExperienceDetails> experience = new ArrayList<>();

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mUser = mAuth.getCurrentUser();
    DatabaseReference mData=FirebaseDatabase.getInstance().getReference("users/"+mUser.getUid()+"/profiles/");
    String resume_id ;
    ProgressDialog progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_experience, container, false);

        resume_id = ((UserInputData)getActivity()).resume_id;

        experience_add =view.findViewById(R.id.experience_add);
        experience_save = view.findViewById(R.id.experience_save);
        experience_list = view.findViewById(R.id.experience_list);

        mData.child(resume_id).addListenerForSingleValueEvent(new ValueEventListener(){
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot){
               progress = new ProgressDialog(getContext());
               progress.setTitle("Progress");
               progress.setMessage("data is fetching...");
               progress.setCancelable(false);
               progress.show();
               resume_profile = snapshot.getValue(Profile.class);
               if(resume_profile!=null){
                   if(resume_profile.experience!=null){
                       experience = resume_profile.experience;
                   }
               }
               else{
                   resume_profile = new Profile();
               }
               experience_list.setLayoutManager(new LinearLayoutManager(getContext()));
               adapter = new AdapterExperience(getContext() , experience);
               experience_list.setAdapter(adapter);
               progress.dismiss();
           }
           @Override
           public void onCancelled(@NonNull DatabaseError error){
               throw error.toException();
           }
        });

        experience_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getLayoutInflater();
                View dialog_view = inflater.inflate(R.layout.dialog_experience , null);
                dialog.setView(dialog_view);

                dialog.setNeutralButton("Cancel" , new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface , int i){
                        Toast.makeText(getContext(), "Cancelled" ,Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.setPositiveButton("Submit" , new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface , int i){
                        String company = ((EditText)dialog_view.findViewById(R.id.input_company)).getText().toString();
                        String job = ((EditText)dialog_view.findViewById(R.id.input_job)).getText().toString();
                        String start = ((EditText)dialog_view.findViewById(R.id.input_start)).getText().toString();
                        String end = ((EditText)dialog_view.findViewById(R.id.input_end)).getText().toString();
                        String details = ((EditText)dialog_view.findViewById(R.id.input_details)).getText().toString();
                        if(company.isEmpty() || job.isEmpty() || start.isEmpty() || end.isEmpty() || details.isEmpty()){
                            Toast.makeText(getContext(),"fill all inputs",LENGTH_SHORT).show();
                        }
                        else{
                            experience.add(new Profile.ExperienceDetails(company , job , start , end , details));
                            adapter.notifyItemInserted(experience.size()-1);
                            experience_list.scrollToPosition(experience.size()-1);
                        }
                    }
                });
                dialog.create();
                dialog.show();
            }
        });

        experience_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                progress.setMessage("uploading...");
                progress.setCancelable(false);
                progress.show();
                resume_profile.experience = experience;
                mData.child(resume_id).setValue(resume_profile);
                progress.dismiss();
                Toast.makeText(getContext() , "Your data is saved , you can go back" , LENGTH_LONG).show();
            }
        });

        return view;
    }
}