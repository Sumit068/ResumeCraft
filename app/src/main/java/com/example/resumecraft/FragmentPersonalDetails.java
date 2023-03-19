package com.example.resumecraft;

import static android.app.Activity.RESULT_OK;
import static android.widget.Toast.LENGTH_SHORT;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class FragmentPersonalDetails extends Fragment {

    EditText input_personal_name , input_personal_email , input_personal_number , input_personal_address;
    ImageView input_personal_image;
    private final int REQ_CODE = 1;
    Button personal_image_upload , personal_image_remove , personal_details_save;
    Profile resume_profile;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mUser = mAuth.getCurrentUser();
    DatabaseReference mData=FirebaseDatabase.getInstance().getReference("users/"+mUser.getUid()+"/profiles/");
    StorageReference mStorage= FirebaseStorage.getInstance().getReference("users/"+mUser.getUid()+"/images/");
    Uri uri;
    ProgressDialog progress ;
    String resume_id ;
    String temp_img;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_personal_details, container, false);

        resume_id = ((UserInputData)getActivity()).resume_id;

        input_personal_name =view.findViewById(R.id.input_personal_name);
        input_personal_email = view.findViewById(R.id.input_personal_email);
        input_personal_number = view.findViewById(R.id.input_personal_number);
        input_personal_address = view.findViewById(R.id.input_personal_address);
        input_personal_image = view.findViewById(R.id.input_personal_image);
        personal_image_upload = view.findViewById(R.id.personal_image_upload);
        personal_image_remove = view.findViewById(R.id.personal_image_remove);
        personal_details_save = view.findViewById(R.id.personal_details_save);

        mData.child(resume_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                progress = new ProgressDialog(getContext());
                progress.setTitle("Progress");
                progress.setMessage("Data is fetching...");
                progress.setCancelable(false);
                progress.show();
                resume_profile = snapshot.getValue(Profile.class);
                if(resume_profile!=null){
                    if(resume_profile.name != null){
                        input_personal_name.setText(resume_profile.name);
                    }
                    if(resume_profile.email!=null){
                        input_personal_email.setText(resume_profile.email);
                    }
                    if(resume_profile.number!=null){
                        input_personal_number.setText(resume_profile.number);
                    }
                    if(resume_profile.address!=null){
                        input_personal_address.setText(resume_profile.address);
                    }
                    if(resume_profile.image!=null && !resume_profile.image.isEmpty()){
                        temp_img = resume_profile.image;
                        Glide.with(getContext())
                                .load(resume_profile.image)
                                .thumbnail(0.1f)
                                .addListener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        progress.dismiss();
                                        Toast.makeText(getContext(),"image loading error : select and save then refresh",LENGTH_SHORT).show();
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        progress.dismiss();
                                        return false;
                                    }
                                })
                                .into(input_personal_image);
                    }
                    progress.dismiss();
                }
                else{
                    resume_profile = new Profile();
                    progress.dismiss();
                }
            }
            public void onCancelled(@NonNull DatabaseError error){
                throw error.toException();
            }
        });

        personal_image_upload.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                pickImage();
            }
        });

        input_personal_image.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                pickImage();
            }
        });

        personal_image_remove.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                uri=null;
                progress.setMessage("removing");
                progress.setCancelable(false);
                progress.show();
                temp_img = "";
                input_personal_image.setImageResource(R.drawable.ic_person);
                progress.dismiss();
            }
        });

        personal_details_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                resume_profile.name = input_personal_name.getText().toString();
                resume_profile.email = input_personal_email.getText().toString();
                resume_profile.number = input_personal_number.getText().toString();
                resume_profile.address = input_personal_address.getText().toString();
                if(resume_profile.name.isEmpty()){
                    input_personal_name.setError("please enter name");
                }
                else if(resume_profile.email.isEmpty()){
                    input_personal_email.setError("please enter e-mail");
                }
                else if(resume_profile.number.isEmpty()){
                    input_personal_number.setError("please enter number");
                }
                else if(resume_profile.address.isEmpty()){
                    input_personal_number.setError("please enter address");
                }
                else{
                    progress.setTitle("Progress");
                    progress.setMessage("Uploading...");
                    progress.setCancelable(false);
                    progress.show();
                    if(uri!=null){
                        String name = getName(uri);
                        if(resume_profile.image !=null && !resume_profile.image.isEmpty()){
                            deletePreviousImage(resume_profile.image);
                        }
                        uploadImage(name);
                    }
                    else{
                        if(resume_profile.image!=temp_img && (resume_profile.image!=null && !resume_profile.image.isEmpty())){
                            deletePreviousImage(resume_profile.image);
                            resume_profile.image="";
                        }
                        mData.child(resume_id).setValue(resume_profile);
                        progress.dismiss();
                        Toast.makeText(getContext() , "data saved, you can go back" , LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }

    private void pickImage(){
        Intent iGallery = new Intent(Intent.ACTION_PICK);
        iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(iGallery,REQ_CODE);
    }

    private String getName(Uri uri){
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return System.currentTimeMillis()+"."+mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void deletePreviousImage(String url){
        StorageReference url_ref = FirebaseStorage.getInstance().getReferenceFromUrl(url);
        url_ref.delete().addOnSuccessListener(new OnSuccessListener<Void>(){

            @Override
            public void onSuccess(Void unused) {
                Log.d("delete","passed");
            }
        }).addOnFailureListener(new OnFailureListener(){

            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("delete","failed");
            }
        });
    }
    public void uploadImage(String name){
        mStorage.child(name).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mStorage.child(name).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>(){

                    @Override
                    public void onSuccess(Uri uri) {
                        resume_profile.image = uri.toString();
                        mData.child(resume_id).setValue(resume_profile);
                        progress.dismiss();
                        Toast.makeText(getContext() , "data saved, you can go back" , LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener(){

            @Override
            public void onFailure(@NonNull Exception e) {
                progress.dismiss();
                Toast.makeText(getContext(),"image uploading failed...", LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == REQ_CODE){
            uri = data.getData();
            input_personal_image.setImageURI(uri);
        }
    }
}
