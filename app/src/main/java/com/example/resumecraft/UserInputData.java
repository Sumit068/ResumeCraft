package com.example.resumecraft;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserInputData extends AppCompatActivity {

    ConstraintLayout create_layout;
    TextView personal_details, education_details, experience_details, skill_details, project_details, objective_details, others_details;
    ConstraintLayout view_your_resume;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mUser = mAuth.getCurrentUser();
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference("users/"+mUser.getUid()+"/profiles/");
    String resume_id ;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input_data);

        resume_id = getIntent().getStringExtra("resume_id");
        create_layout = findViewById(R.id.create_layout);
        personal_details = findViewById(R.id.personal_details);
        education_details = findViewById(R.id.education_details);
        experience_details = findViewById(R.id.experience_details);
        skill_details = findViewById(R.id.skill_details);
        project_details = findViewById(R.id.project_details);
        objective_details = findViewById(R.id.objective_details);
        others_details = findViewById(R.id.others_details);
        view_your_resume = findViewById(R.id.view_your_resume);

        view_your_resume.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                progress = new ProgressDialog(UserInputData.this);
                progress.setTitle("Progress");
                progress.setMessage("Please wait...");
                progress.setCancelable(false);
                progress.show();
                mData.child(resume_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Profile profile = snapshot.getValue(Profile.class);
                        if(profile != null && profile.name!=null && !profile.name.isEmpty()){
                            Intent intent = new Intent(UserInputData.this,TemplateList.class);
                            intent.putExtra("resume_id",resume_id);
                            progress.dismiss();
                            startActivity(intent);
                        }
                        else{
                            progress.dismiss();
                            Toast.makeText(getApplicationContext(), "please fill personal details" , Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progress.dismiss();
                        throw error.toException();
                    }
                });
            }
        });

        personal_details.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                changeFragment("personal_details_fragment");
            }
        });
        education_details.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                changeFragment("education_details_fragment");
            }
        });
        experience_details.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                changeFragment("experience_details_fragment");
            }
        });
        skill_details.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                changeFragment("skill_details_fragment");
            }
        });
        project_details.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                changeFragment("project_details_fragment");
            }
        });
        objective_details.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                changeFragment("objective_details_fragment");
            }
        });
        others_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment("others_details");
            }
        });
    }
    protected void changeFragment(@NonNull String fragment_name){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        switch (fragment_name){
            case "personal_details_fragment" : {
                FragmentPersonalDetails fragment = new FragmentPersonalDetails();
                transaction.replace(R.id.create_layout, fragment);
                break;
            }
            case "education_details_fragment" : {
                FragmentEducation fragment = new FragmentEducation();
                transaction.replace(R.id.create_layout, fragment);
                break;
            }
            case "experience_details_fragment" : {
                FragmentExperience fragment = new FragmentExperience();
                transaction.replace(R.id.create_layout, fragment);
                break;
            }
            case "skill_details_fragment" :{
                FragmentSkill fragment = new FragmentSkill();
                transaction.replace(R.id.create_layout, fragment);
                break;
            }
            case "project_details_fragment" : {
                FragmentProject fragment = new FragmentProject();
                transaction.replace(R.id.create_layout, fragment);
                break;
            }
            case "objective_details_fragment" : {
                FragmentObjective fragment = new FragmentObjective();
                transaction.replace(R.id.create_layout, fragment);
                break;
            }
            case "others_details":{
                FragmentOthers fragment = new FragmentOthers();
                transaction.replace(R.id.create_layout, fragment);
                break;
            }
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }
}