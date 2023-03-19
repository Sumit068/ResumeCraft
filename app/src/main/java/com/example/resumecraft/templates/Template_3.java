package com.example.resumecraft.templates;

import com.example.resumecraft.Profile;

import java.util.ArrayList;
import java.util.List;

public class Template_3 {
    public String get(Profile profile){
        StringBuilder web_page = new StringBuilder();
        web_page.append("<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "        <style>\n" +
                "            body{\n" +
                "                margin:0;\n" +
                "                width: 210mm;\n" +
                "                min-height:297mm;\n" +
                "            }\n" +
                "            .personal{\n" +
                "                display: grid;\n" +
                "                max-height: 35mm;\n" +
                "                grid-template-columns: auto 35mm;\n" +
                "            }\n" +
                "            img{\n" +
                "                height: 35mm;\n" +
                "                width:35mm;\n" +
                "            }\n" +
                "            .headings{\n" +
                "                margin-top: 15px;\n" +
                "                padding-left:5px;\n" +
                "                background-color: #BEBEBE;\n" +
                "            }\n" +
                "            div > ul {\n" +
                "                list-style-position:inside;\n" +
                "                padding-left:5px;\n" +
                "                margin:0;\n" +
                "            }\n" +
                "            table{\n" +
                "                border-top: 1px solid black;\n" +
                "                border-bottom: 1px solid black;\n" +
                "                margin-top: 5px;\n" +
                "                width: 100%;\n" +
                "            }\n" +
                "            th{\n" +
                "                border-bottom: 1px solid black;\n" +
                "                text-align: left;\n" +
                "            }\n" +
                "            .job{\n" +
                "                font-size: 14px;\n" +
                "                font-style: italic;\n" +
                "                margin-left: 21px;\n" +
                "            }\n" + "address{\n" +
                "                max-width: 50%;\n" +
                "                word-wrap: break-word;\n" +
                "            }"+
                "        </style> \n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <div class=\"personal\">\n" +
                "            <div>\n" +
                "                <span><b>"+profile.name+"</b></span><br>\n" +
                "                <address>"+profile.address+"</address>\n" +
                "                <span>Email Id :- <b>"+profile.email+"</b></span><br>\n" +
                "                <span>Mobile no. :- <b>"+profile.number+"</b></span><br>\n" +
                "            </div>\n");
        if(profile.image!=null && !profile.image.isEmpty()) {
            web_page.append("<div>\n" +
                    "                <img src=\"" + profile.image + "\">\n" +
                    "            </div>\n");
        }
        web_page.append("</div>\n");
        if(profile.objective!=null && !profile.objective.isEmpty()) {
            web_page.append("<div>\n" +
                    "            <div class=\"headings\">Objective</div>\n" +
                    "            <ul><li>"+profile.objective+"</li></ul>\n" +
                    "        </div>\n");
        }
        if(profile.education!=null && !profile.education.isEmpty()) {
            web_page.append("<div>\n" +
                    "            <div class=\"headings\">Acadmic details</div>\n" +
                    "            <table cellspacing=\"0\">\n" +
                    "                <tr>\n" +
                    "                    <th>Examination</thtyle=>\n" +
                    "                    <th>University</th>\n" +
                    "                    <th>Institute</th>\n" +
                    "                    <th>Year</th>\n" +
                    "                    <th>CPI/%</th>\n" +
                    "                </tr>\n");
            for (Profile.EducationDetails i : profile.education) {
                web_page.append("<tr>\n" +
                        "                    <td>"+i.degree+"</td>\n" +
                        "                    <td>"+i.university+"</td>\n" +
                        "                    <td>"+i.school+"</td>\n" +
                        "                    <td>"+i.year+"</td>\n" +
                        "                    <td>"+i.score+"</td>\n" +
                        "                </tr>\n");
            }
            web_page.append("</table>\n" +
                    "        </div>\n");
        }
        if(profile.intrest!=null && !profile.intrest.isEmpty()) {
            web_page.append("<div>\n" +
                    "            <div class=\"headings\">Field of Interest</div>\n" +
                    "            <ul><li>"+profile.intrest+"</li></ul>\n" +
                    "        </div>\n");
        }
        if(profile.skills!=null && !profile.skills.isEmpty()) {
            web_page.append("<div>\n" +
                    "            <div class=\"headings\">Tchnical Skills</div>\n" +
                    "            <ul><li>"+merge(profile.skills)+"</li></ul>\n" +
                    "        </div>\n");
        }
        if(profile.experience!=null && !profile.experience.isEmpty()) {
            web_page.append("<div>\n" +
                    "            <div class=\"headings\">Work Experience</div>\n" +
                    "            <ul>\n");
            for (Profile.ExperienceDetails i : profile.experience) {
                web_page.append("<li>\n" +
                        "                    <span>" + i.company + "</span><br>\n" +
                        "                    <span class=\"job\">" + i.job + " , " + i.start + " - " + i.end + "</span>\n" +
                        "                    <ul>\n");
                for (String j : this.parts(i.details)) {
                    web_page.append("<li>" + j + "</li>");
                }
                web_page.append("</ul>\n" +
                        "                </li>\n");
            }
            web_page.append("</ul>\n" +
                    "        </div>\n");
        }
        if(profile.project!=null && !profile.project.isEmpty()) {
            web_page.append("<div>\n" +
                    "            <div class=\"headings\">Projects</div>\n" +
                    "            <ul>\n");
            for (Profile.ProjectDetails i : profile.project) {
                web_page.append("<li>\n" +
                        "                    <span>" + i.project + "</span>\n" +
                        "                    <ul>\n");
                for (String j : this.parts(i.description)) {
                    web_page.append("<li>" + j + "</li>");
                }
                web_page.append("</ul>\n" +
                        "                </li>");
            }
            web_page.append("</ul>\n" +
                    "        </div>");
        }
        if(profile.strength!=null && !profile.strength.isEmpty()) {
            web_page.append("<div>\n" +
                    "            <div class=\"headings\">Strength</div>\n" +
                    "            <ul><li>"+profile.strength+"</li></ul>\n" +
                    "        </div>\n");
        }
        if(profile.hobbies!=null && !profile.hobbies.isEmpty()) {
            web_page.append("<div>\n" +
                    "            <div class=\"headings\">Hobbies</div>\n" +
                    "            <ul><li>"+profile.hobbies+"</li></ul>\n" +
                    "        </div>\n");
        }
                    web_page.append("</body>\n" +
                "</html>");
        return web_page.toString();
    }
    List<String> parts(String str){
        List<String> list = new ArrayList<>();
        for(String i:str.split("\\.")){
            i=i.trim();
            if(i!=null && !i.isEmpty()){
                list.add(i.trim());
            }
        }
        return list;
    }

    String merge(ArrayList<Profile.SkillDetails> list){
        StringBuilder str = new StringBuilder();
        for(Profile.SkillDetails i:list){
            str.append(i.skill+", ");
        }
        str.deleteCharAt(str.length()-2);
        return str.toString();
    }
}
