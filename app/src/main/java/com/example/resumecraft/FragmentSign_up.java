package com.example.resumecraft;

import static android.widget.Toast.LENGTH_SHORT;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FragmentSign_up extends Fragment {

    TextView jumpToLogin;

    EditText signup_email , signup_password , signup_confirmPassword;
    Button signup_btn;
    FirebaseAuth mAuth;

    ProgressDialog progress;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        jumpToLogin = view.findViewById(R.id.jumpToLogin);

        signup_email = view.findViewById(R.id.signup_email);
        signup_password = view.findViewById(R.id.signup_password);
        signup_confirmPassword = view.findViewById(R.id.signup_confirmPassword);
        signup_btn = view.findViewById(R.id.signup_btn);
        mAuth = FirebaseAuth.getInstance();

        signup_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                progress = new ProgressDialog(getContext());
                progress.setTitle("Progress");
                progress.setMessage("Please wait...");
                progress.setCancelable(false);
                progress.show();
                String email , password , confirmPassword;
                email = signup_email.getText().toString();
                password = signup_password.getText().toString();
                confirmPassword = signup_confirmPassword.getText().toString();
                String emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";

                if(!email.matches(emailPattern)){
                    signup_email.setError("enter valid email");
                }
                else if(password.length()<6){
                    signup_password.setError("enter proper password");
                }
                else if(!password.equals(confirmPassword)){
                    signup_confirmPassword.setError("password didn't match");
                }
                else{
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                        @Override
                        public void onComplete(Task<AuthResult> task){
                            if(task.isSuccessful()){
                                Toast.makeText(getActivity(),"sucessfully sign up",LENGTH_SHORT).show();
                                FragmentLog_in login = new FragmentLog_in();
                                FragmentTransaction transactionToLogin = getActivity().getSupportFragmentManager().beginTransaction();
                                progress.dismiss();
                                transactionToLogin.replace(R.id.lastLayout,login).commit();
                            }
                            else{
                                progress.dismiss();
                                Toast.makeText(getActivity(),""+task.getException(),LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        jumpToLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                FragmentLog_in login = new FragmentLog_in();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.lastLayout,login).commit();
            }
        });
        return view;
    }
}