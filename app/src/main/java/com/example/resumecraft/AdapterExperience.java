package com.example.resumecraft;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterExperience extends RecyclerView.Adapter<AdapterExperience.ViewHolder>{

    Context context;
    ArrayList<Profile.ExperienceDetails> experience;

    AdapterExperience(Context context , ArrayList<Profile.ExperienceDetails> experience){
        this.context = context;
        this.experience = experience;
    }

    //@NonNull
    @Override
    public AdapterExperience.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_experience , parent , false);
        AdapterExperience.ViewHolder holder = new AdapterExperience.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterExperience.ViewHolder holder, int position) {
        holder.company.setText(experience.get(position).company);
        holder.job.setText(experience.get(position).job);
        holder.start.setText(experience.get(position).start);
        holder.end.setText(experience.get(position).end);
        holder.details.setText(experience.get(position).details);
        holder.delete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                experience.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return experience==null?0:experience.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView company , job , start , end , details;
        ImageButton delete;
        public ViewHolder(View view){
            super(view);
            company = view.findViewById(R.id.company);
            job = view.findViewById(R.id.job);
            start = view.findViewById(R.id.start);
            end = view.findViewById(R.id.end);
            details = view.findViewById(R.id.details);
            delete = itemView.findViewById(R.id.delete_experience);
        }
    }
}
