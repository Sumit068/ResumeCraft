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

public class FragmentSkill extends Fragment {

    Button skill_add , skill_save;
    RecyclerView skill_list;
    Profile resume_profile;
    AdapterSkill adapter;
    ArrayList<Profile.SkillDetails> skills = new ArrayList<>();

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mUser = mAuth.getCurrentUser();
    DatabaseReference mData=FirebaseDatabase.getInstance().getReference("users/"+mUser.getUid()+"/profiles/");
    String resume_id ;
    ProgressDialog progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_skill, container, false);

        resume_id = ((UserInputData)getActivity()).resume_id;

        skill_add = view.findViewById(R.id.skill_add);
        skill_save = view.findViewById(R.id.skill_save);
        skill_list = view.findViewById(R.id.skill_list);

        mData.child(resume_id).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progress = new ProgressDialog(getContext());
                progress.setTitle("Progress");
                progress.setMessage("Data is fetching...");
                progress.setCancelable(false);
                progress.show();
                resume_profile = snapshot.getValue(Profile.class);
                if(resume_profile!= null){
                    if(resume_profile!=null && resume_profile.skills!=null){
                        skills = resume_profile.skills;
                    }
                }
                else{
                    resume_profile = new Profile();
                }
                skill_list.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new AdapterSkill(getContext() , skills);
                skill_list.setAdapter(adapter);
                progress.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });

        skill_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getLayoutInflater();
                View dialog_view = inflater.inflate(R.layout.dialog_skill , null);
                dialog.setView(dialog_view);

                dialog.setNeutralButton("Cencel" , new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface , int i){
                        Toast.makeText(getActivity() , "Cancelled" , LENGTH_SHORT).show();
                    }
                });

                dialog.setPositiveButton("Submit" , new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface , int i){
                        String skill = ((EditText)dialog_view.findViewById(R.id.input_skill)).getText().toString();
                        String level = ((EditText)dialog_view.findViewById(R.id.input_level)).getText().toString();
                        if(skill.isEmpty() || level.isEmpty()){
                            Toast.makeText(getContext(),"fill all inputs",LENGTH_SHORT).show();
                        }
                        else if(Integer.parseInt(level)<0 || Integer.parseInt(level)>100){
                            Toast.makeText(getContext(),"invalid level... level range is 0 to 100",LENGTH_SHORT).show();
                        }
                        else{
                            skills.add(new Profile.SkillDetails(skill,level));
                            adapter.notifyItemInserted(skills.size()-1);
                            skill_list.scrollToPosition(skills.size()-1);
                        }
                    }
                });
                dialog.create();
                dialog.show();
            }
        });

        skill_save.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                progress.setMessage("uploading...");
                progress.setCancelable(false);
                progress.show();
                resume_profile.skills = skills;
                mData.child(resume_id).setValue(resume_profile);
                progress.dismiss();
                Toast.makeText(getContext() , "Your data is saved , you can go back" , LENGTH_LONG).show();
            }
        });

        return view;
    }
}