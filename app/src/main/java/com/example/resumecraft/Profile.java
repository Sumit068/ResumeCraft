package com.example.resumecraft;

import java.util.ArrayList;

public class Profile {
    public String name, email , number , address ,image , objective, hobbies, strength, intrest;
    public ArrayList<EducationDetails> education = new ArrayList<>();
    public ArrayList<ExperienceDetails> experience = new ArrayList<>() ;
    public ArrayList<ProjectDetails> project = new ArrayList<>();
    public ArrayList<SkillDetails> skills = new ArrayList<>();

    public static class SkillDetails{
        public String skill;
        public String level;
        SkillDetails(){
            //Empty Constructor
        }
        SkillDetails(String skill , String level){
            this.skill = skill;
            this.level = level;
        }
    }

    public static class EducationDetails {
        public String degree;
        public String school;
        public String university;
        public String year;
        public String score;
        public EducationDetails(){
            //empty constructor
        }
        EducationDetails(String degree , String school ,String university, String score , String year){
            this.degree = degree;
            this.school = school;
            this.university = university;
            this.score = score;
            this.year = year;
        }
    }

    public static class ExperienceDetails{
        public String company;
        public String job;
        public String start;
        public String end;
        public String details;
        public ExperienceDetails(){
            //Empty Constructor
        }
        ExperienceDetails(String company , String job , String start ,  String end , String details){
            this.company = company;
            this.job = job;
            this.start = start;
            this.end = end;
            this.details = details;
        }
    }

    public static class ProjectDetails{
        public String project;
        public String description;
        ProjectDetails(){
            //Empty constructor
        }
        ProjectDetails(String project , String description){
            this.project = project;
            this.description = description;
        }
    }
}




