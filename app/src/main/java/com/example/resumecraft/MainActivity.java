package com.example.resumecraft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView jumpToOtherFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jumpToOtherFragment = findViewById(R.id.jump_to_signup);

        FragmentLog_in login = new FragmentLog_in();
        FragmentTransaction transactionToLogin = getSupportFragmentManager().beginTransaction();
        transactionToLogin.replace(R.id.lastLayout,login);
        transactionToLogin.commit();
    }
}