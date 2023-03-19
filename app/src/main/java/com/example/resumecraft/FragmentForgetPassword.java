package com.example.resumecraft;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class FragmentForgetPassword extends Fragment {

    EditText forget_email;
    Button send_mail_button , cancel_button;
    String emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ProgressDialog progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_forget_password, container, false);

        forget_email= view.findViewById(R.id.forget_email);
        send_mail_button = view.findViewById(R.id.send_mail_button);
        cancel_button = view.findViewById(R.id.cancel_button);

        send_mail_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                progress = new ProgressDialog(getContext());
                progress.setTitle("Progress");
                progress.setMessage("Please wait...");
                progress.setCancelable(false);
                progress.show();
                String email = forget_email.getText().toString();
                if(!email.matches(emailPattern)){
                    forget_email.setError("enter valid email");
                }
                else{
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>(){

                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                progress.dismiss();
                                jump_to_login();
                                Toast.makeText(getContext(),"check your mail",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                progress.dismiss();
                                Toast.makeText(getContext(),"try again",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                jump_to_login();
            }
        });
        return view;
    }

    private void jump_to_login(){
        FragmentLog_in log_in = new FragmentLog_in();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.lastLayout,log_in).commit();
    }
}