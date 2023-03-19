package com.example.resumecraft;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class SavedResumeAdapter extends RecyclerView.Adapter<SavedResumeAdapter.ViewHolder> {

    Context context;
    ArrayList<Profile> profiles;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mUser = mAuth.getCurrentUser();
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference("users/"+mUser.getUid());
    StorageReference url_ref;
    ProgressDialog progress;

    SavedResumeAdapter(Context context , ArrayList<Profile> profiles){
        this.context = context;
        this.profiles = profiles;
    }

    @NonNull
    @Override
    public SavedResumeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_saved_resume, parent , false);
        SavedResumeAdapter.ViewHolder holder = new SavedResumeAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SavedResumeAdapter.ViewHolder holder, int position) {
        holder.display_name.setText(profiles.get(position).name);
        holder.display_email.setText(profiles.get(position).email);
        if(profiles.get(position).image!=null && !profiles.get(position).image.isEmpty()){
            Glide.with(context).load(profiles.get(position).image).thumbnail(0.1f).into(holder.display_image);
        }

        holder.edit_resume.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , UserInputData.class);
                intent.putExtra("resume_id",""+holder.getAdapterPosition());
                context.startActivity(intent);
            }
        });

        holder.preview_resume.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , TemplateList.class);
                intent.putExtra("resume_id",""+holder.getAdapterPosition());
                context.startActivity(intent);
            }
        });

        holder.delete_profile.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                progress = new ProgressDialog(context);
                progress.setTitle("deleting...");
                progress.setCancelable(false);
                progress.show();
                int pos = holder.getAdapterPosition();
                if(profiles.get(pos).image!=null && !profiles.get(pos).image.isEmpty()){
                    url_ref = FirebaseStorage.getInstance().getReferenceFromUrl(profiles.get(pos).image);
                    url_ref.delete().addOnSuccessListener(new OnSuccessListener<Void>(){

                        @Override
                        public void onSuccess(Void unused) {
                            profiles.remove(pos);
                            mData.child("profiles").setValue(profiles);
                            notifyItemRemoved(pos);
                            progress.dismiss();
                        }
                    });
                }
                else{
                    profiles.remove(pos);
                    mData.child("profiles").setValue(profiles);
                    notifyItemRemoved(pos);
                    progress.dismiss();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView display_image;
        ImageButton delete_profile;
        TextView display_name , display_email;
        ConstraintLayout edit_resume , preview_resume;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            display_image=itemView.findViewById(R.id.display_image);
            display_name=itemView.findViewById(R.id.display_name);
            display_email=itemView.findViewById(R.id.display_email);
            edit_resume=itemView.findViewById(R.id.edit_resume);
            preview_resume=itemView.findViewById(R.id.preview_resume);
            delete_profile = itemView.findViewById(R.id.delete_profile);
        }
    }
}
