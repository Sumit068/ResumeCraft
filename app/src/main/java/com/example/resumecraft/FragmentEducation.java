package com.example.resumecraft;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

import androidx.annotation.NonNull;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

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

public class FragmentEducation extends Fragment {

    Profile resume_profile;
    ArrayList<Profile.EducationDetails> education = new ArrayList<>();
    RecyclerView education_list;
    Button education_add , education_save;
    AdapterEducation adapter;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mUser = mAuth.getCurrentUser();
    DatabaseReference mData=FirebaseDatabase.getInstance().getReference("users/"+mUser.getUid()+"/profiles/");
    String resume_id ;
    ProgressDialog progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_education, container, false);

        resume_id = ((UserInputData)getActivity()).resume_id;

        education_add = view.findViewById(R.id.education_add);
        education_save = view.findViewById(R.id.education_save);
        education_list  = view.findViewById(R.id.education_list);

        mData.child(resume_id).addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progress = new ProgressDialog(getContext());
                progress.setTitle("Progress");
                progress.setMessage("Data is fetching...");
                progress.setCancelable(false);
                progress.show();
                resume_profile = snapshot.getValue(Profile.class);
                if(resume_profile!=null){
                    if(resume_profile.education!=null){
                        education = resume_profile.education;
                    }
                }
                else{
                    resume_profile = new Profile();
                }
                education_list.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new AdapterEducation(getContext() , education );
                education_list.setAdapter(adapter);
                progress.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });

        education_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getLayoutInflater();
                View dialog_view = inflater.inflate(R.layout.dialog_education , null);
                dialog.setView(dialog_view);

                dialog.setNeutralButton("Cancel" , new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface , int i){
                        Toast.makeText(getContext(),"Cancelled",LENGTH_SHORT).show();
                    }
                });

                dialog.setPositiveButton("Submit" , new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface , int i){
                        String education_degree = ((EditText)dialog_view.findViewById(R.id.input_education_degree)).getText().toString();
                        String education_school = ((EditText)dialog_view.findViewById(R.id.input_education_school)).getText().toString();
                        String education_score = ((EditText)dialog_view.findViewById(R.id.input_education_score)).getText().toString();
                        String education_year = ((EditText)dialog_view.findViewById(R.id.input_education_year)).getText().toString();
                        String education_university = ((EditText)dialog_view.findViewById(R.id.input_education_university)).getText().toString();
                        if(education_degree.isEmpty() || education_school.isEmpty() || education_score.isEmpty() || education_year.isEmpty()){
                            Toast.makeText(getContext(),"fill all inputs",LENGTH_SHORT).show();
                        }
                        else{
                            education.add(new Profile.EducationDetails(education_degree , education_school ,education_university, education_score , education_year));
                            adapter.notifyItemInserted(education.size()-1);
                            education_list.scrollToPosition(education.size()-1);
                        }
                    }
                });
                dialog.create();
                dialog.show();
            }
        });

        education_save.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){
               progress.setMessage("uploading...");
               progress.setCancelable(false);
               progress.show();
               resume_profile.education = education;
               mData.child(resume_id).setValue(resume_profile);
               progress.dismiss();
               Toast.makeText(getContext() , "Your data is saved , you can go back" , LENGTH_LONG).show();
           }
        });
        return view;
    }
}