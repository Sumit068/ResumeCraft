package com.example.resumecraft;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){

                mAuth=FirebaseAuth.getInstance();
                SharedPreferences logged_in = getSharedPreferences("user" , MODE_PRIVATE);
                if(logged_in.getString("user_email","")!=""){
                    String email = logged_in.getString("user_email" , "");
                    String password = logged_in.getString("user_password" , "");
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task){
                            if(task.isSuccessful()){
                                Intent intent = new Intent(SplashScreen.this,HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
                else{
                    Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },  1000);
    }
}