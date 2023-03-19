package com.example.resumecraft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class TemplateList extends AppCompatActivity {

    RecyclerView templates_list;
    AdapterTemplates adapter;
    ArrayList<Templates> list = new ArrayList<>();
    String resume_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_list);

        resume_id = getIntent().getStringExtra("resume_id");
        templates_list=findViewById(R.id.templates_list);

        list.add(new Templates(R.drawable.template_1,0));
        list.add(new Templates(R.drawable.template_2,1));
        list.add(new Templates(R.drawable.template_3,2));


        templates_list.setLayoutManager(new GridLayoutManager(this,2));
        adapter=new AdapterTemplates(this,list,resume_id);
        templates_list.setAdapter(adapter);

    }
}