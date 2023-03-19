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

public class AdapterEducation extends RecyclerView.Adapter<AdapterEducation.ViewHolder> {

    Context context;
    ArrayList<Profile.EducationDetails> education;
    AdapterEducation(Context context , ArrayList<Profile.EducationDetails> education){
        this.context = context;
        this.education = education;
    }

    //@NonNull
    @Override
    public AdapterEducation.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_education, parent , false);
        AdapterEducation.ViewHolder view_holder = new AdapterEducation.ViewHolder(view);
        return view_holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.education_degree.setText(education.get(position).degree);
        holder.education_school.setText(education.get(position).school);
        holder.education_university.setText(education.get(position).university);
        holder.education_score.setText(education.get(position).score);
        holder.education_year.setText(education.get(position).year);
        holder.delete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                education.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }


    @Override
    public int getItemCount() {
        return education == null ? 0 : education.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView education_degree , education_school , education_score , education_year, education_university;
        ImageButton delete;
        public ViewHolder(View itemView){
            super(itemView);
            education_degree = itemView.findViewById(R.id.education_degree);
            education_school = itemView.findViewById(R.id.education_school);
            education_score = itemView.findViewById(R.id.education_score);
            education_year = itemView.findViewById(R.id.education_year);
            education_university = itemView.findViewById(R.id.education_university);
            delete = itemView.findViewById(R.id.delete_education);
        }
    }

}
