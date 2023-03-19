package com.example.resumecraft;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class AdapterTemplates extends RecyclerView.Adapter<AdapterTemplates.ViewHolder>{

    ArrayList<Templates> list= new ArrayList<>();
    Context context;
    Profile resume_profile;
    String resume_id;

    AdapterTemplates(Context context , ArrayList<Templates> list , String resume_id){
        this.context=context;
        this.list=list;
        this.resume_id = resume_id;
    }

    @NonNull
    @Override
    public AdapterTemplates.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_templates,parent,false);
        AdapterTemplates.ViewHolder holder = new AdapterTemplates.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.display_template.setImageResource(list.get(position).img);

        holder.display_template.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ViewResume.class);
                intent.putExtra("resume_id",resume_id);
                intent.putExtra("template",""+holder.getAdapterPosition());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton display_template;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            display_template=itemView.findViewById(R.id.display_template);
        }
    }
}
