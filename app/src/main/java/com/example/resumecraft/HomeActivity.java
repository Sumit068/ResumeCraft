package com.example.resumecraft;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private long timeBack;
    Button create_resume , download_resume , log_out;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mUser = mAuth.getCurrentUser();
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference("users/"+mUser.getUid());
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        create_resume = findViewById(R.id.create_resume);
        download_resume = findViewById(R.id.download_resume);
        log_out = findViewById(R.id.log_out);
        progress = new ProgressDialog(HomeActivity.this);
        progress.setTitle("Progress");
        progress.setMessage("Loading...");
        progress.setCancelable(false);

        create_resume.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                progress.show();
                mData.child("profiles").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String resume_id = ""+snapshot.getChildrenCount();
                        Intent intent = new Intent(HomeActivity.this , UserInputData.class);
                        intent.putExtra("resume_id",resume_id);
                        progress.dismiss();
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        throw error.toException();
                    }
                });
            }
        });

        download_resume.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(HomeActivity.this , SavedResume.class);
                startActivity(intent);
            }
        });

        log_out.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                progress.show();
                SharedPreferences logged_out = getSharedPreferences("user",MODE_PRIVATE);
                SharedPreferences.Editor editor = logged_out.edit();
                editor.putString("user_email","");
                editor.putString("user_password","");
                editor.apply();

                Intent intent = new Intent(HomeActivity.this , MainActivity.class);
                progress.dismiss();
                startActivity(intent);
                finish();
            }
        });
    }



    @Override
    public void onBackPressed(){
        if(System.currentTimeMillis() - timeBack > 1000){
            timeBack = System.currentTimeMillis();
            Toast.makeText(this, "try again to exit", Toast.LENGTH_SHORT).show();
        }
        else{
            super.onBackPressed();
        }
    }
}