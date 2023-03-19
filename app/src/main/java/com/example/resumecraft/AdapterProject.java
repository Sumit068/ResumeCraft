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

public class AdapterProject extends RecyclerView.Adapter<AdapterProject.ViewHolder>{

    Context context;
    ArrayList<Profile.ProjectDetails> projects;

    AdapterProject(Context context , ArrayList<Profile.ProjectDetails> projects){
        this.context=context;
        this.projects=projects;
    }

    //@NonNull
    @Override
    public AdapterProject.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_project , parent , false);
        AdapterProject.ViewHolder holder = new AdapterProject.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProject.ViewHolder holder, int position) {
        holder.project.setText(projects.get(position).project);
        holder.description.setText(projects.get(position).description);
        holder.delete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                projects.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return projects==null?0:projects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView project , description;
        ImageButton delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            project = itemView.findViewById(R.id.project);
            description = itemView.findViewById(R.id.description);
            delete = itemView.findViewById(R.id.delete_project);
        }
    }
}
