package com.example.resumecraft;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterSkill extends RecyclerView.Adapter<AdapterSkill.ViewHolder>{

    Context context;
    ArrayList<Profile.SkillDetails> skills;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseDatabase mData;

    public AdapterSkill(Context context , ArrayList<Profile.SkillDetails> skills){
        this.context = context;
        this.skills = skills;
    }

    //@NonNull
    @Override
    public AdapterSkill.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_skill , parent,  false);
        AdapterSkill.ViewHolder holder = new AdapterSkill.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSkill.ViewHolder holder, int position) {
        holder.skill.setText(skills.get(position).skill);
        holder.level.setText(skills.get(position).level);
        holder.delete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                skills.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return skills==null?0:skills.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView skill , level;
        ImageButton delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            skill = itemView.findViewById(R.id.skill);
            level = itemView.findViewById(R.id.level);
            delete = itemView.findViewById(R.id.delete_skill);
        }
    }
}
