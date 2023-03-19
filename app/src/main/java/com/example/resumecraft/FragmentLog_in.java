package com.example.resumecraft;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FragmentLog_in extends Fragment {

    EditText login_email , login_password;
    String emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
    Button login_btn;
    TextView jump_to_signup, forget_password;
    FirebaseAuth mAuth;
    ProgressDialog progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_log_in, container, false);

        mAuth= FirebaseAuth.getInstance();
        login_email=view.findViewById(R.id.login_email);
        login_password=view.findViewById(R.id.login_password);
        login_btn=view.findViewById(R.id.login_btn);
        jump_to_signup =view.findViewById(R.id.jump_to_signup);
        forget_password=view.findViewById(R.id.forget_password);

        login_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                progress = new ProgressDialog(getContext());
                progress.setTitle("Progress");
                progress.setMessage("Please wait...");
                progress.setCancelable(false);
                progress.show();
                String email = login_email.getText().toString();
                String password = login_password.getText().toString();

                if(!email.matches(emailPattern)){
                    login_email.setError("enter valid email");
                }
                else if(password.length()<6){
                    login_password.setError("enter valid password");
                }
                else{
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                       public void onComplete(@NonNull Task<AuthResult> task){
                            if(task.isSuccessful()){
                                SharedPreferences logged_in = getContext().getSharedPreferences("user" , MODE_PRIVATE);
                                SharedPreferences.Editor editor = logged_in.edit();
                                editor.putString("user_email",email);
                                editor.putString("user_password",password);
                                editor.apply();

                                Intent intent = new Intent(getActivity(),HomeActivity.class);
                                progress.dismiss();
                                startActivity(intent);
                                getActivity().finish();
                            }
                            else{
                                progress.dismiss();
                                Toast.makeText(getActivity(),"wrong email or password",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        jump_to_signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                FragmentSign_up signup = new FragmentSign_up();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.lastLayout,signup).commit();
            }
        });

        forget_password.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentForgetPassword forget_password = new FragmentForgetPassword();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.lastLayout,forget_password).commit();
            }
        });
        return view;
    }
}