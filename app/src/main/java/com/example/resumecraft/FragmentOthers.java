package com.example.resumecraft;

import static android.widget.Toast.LENGTH_LONG;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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

public class FragmentOthers extends Fragment {

    Button other_save;
    EditText interest, strength, hobbies;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mUser = mAuth.getCurrentUser();
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference("users/"+mUser.getUid()+"/profiles/");
    String resume_id;
    ProgressDialog progress;
    Profile resume_profile;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_others, container, false);

        resume_id = ((UserInputData)getActivity()).resume_id;

        interest = view.findViewById(R.id.interest);
        strength = view.findViewById(R.id.strength);
        hobbies = view.findViewById(R.id.hobbies);
        other_save = view.findViewById(R.id.others_save);

        mData.child(resume_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progress = new ProgressDialog(getContext());
                progress.setTitle("Progress");
                progress.setMessage("Data is fetching...");
                progress.setCancelable(false);
                progress.show();
                resume_profile = snapshot.getValue(Profile.class);
                if(resume_profile!=null){
                    if(resume_profile.intrest!=null){
                        interest.setText(resume_profile.intrest);
                    }
                    if(resume_profile.strength!=null){
                        strength.setText(resume_profile.strength);
                    }
                    if(resume_profile.hobbies!=null){
                        hobbies.setText(resume_profile.hobbies);
                    }
                }
                else{
                    resume_profile = new Profile();

                }
                progress.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });

        other_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setMessage("uploading...");
                progress.setCancelable(false);
                progress.show();
                resume_profile.intrest = interest.getText().toString();
                resume_profile.strength = strength.getText().toString();
                resume_profile.hobbies = hobbies.getText().toString();
                mData.child(resume_id).setValue(resume_profile);
                progress.dismiss();
                Toast.makeText(getContext() , "Your data is saved , you can go back" , LENGTH_LONG).show();
            }
        });
        return view;
    }
}