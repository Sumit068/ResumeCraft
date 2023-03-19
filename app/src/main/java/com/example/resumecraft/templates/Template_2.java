package com.example.resumecraft.templates;

import com.example.resumecraft.Profile;

public class Template_2 {
    public String get(Profile profile){
        StringBuilder web_page = new StringBuilder();
        web_page.append("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <style>\n" +
                "        body{\n" +
                "            margin:0;\n" +
                "        }\n" +
                "        .container{\n" +
                "            font-size: 14px;\n" +
                "            margin: 0;\n" +
                "            width: 210mm;\n" +
                "            min-height:297mm;\n" +
                "            background-color: whitesmoke;\n" +
                "        }\n" +
                "        .upper{\n" +
                "            display: grid;\n" +
                "            max-height:35mm;\n" +
                "            grid-template-columns: 70mm auto 35mm;\n" +
                "            background-color:deepskyblue;\n" +
                "            word-wrap: break-word;\n" +
                "        }\n" +
                "        .lower{\n" +
                "            padding-left:20px;\n" +
                "            padding-right: 20px;\n" +
                "        }\n" +
                "        .heading{\n" +
                "            font-weight:bold;\n" +
                "            font-size: 16px; \n" +
                "            padding-top: 10px;\n" +
                "        }\n" +
                "        img{\n" +
                "            width: 35mm;\n" +
                "            height: 35mm;\n" +
                "        }\n" +
                "        ul{\n" +
                "            margin:0;\n" +
                "            padding-left:20px;\n" +
                "            padding-right:10px;\n" +
                "        }\n" +
                "        .inner{\n" +
                "            padding-left:20px;\n" +
                "            padding-right:10px;\n" +
                "        }\n" +
                "    </style>\n" +
                "    <title>Document</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"upper\">\n" +
                "            <div style=\"font-size:20px; font-weight:bold; padding-left:5px; padding-right: 5px; padding-top: 10px;\">"+profile.name+"\n" +
                "            </div>\n" +
                "            <div style=\"padding-top:10px\">\n" +
                "                <div>\n" +
                "                    <span>"+profile.address+"</span>\n" +
                "                </div>\n" +
                "                <div>\n" +
                "                    <div>Phone number :<b>"+profile.number+"</b></div>\n" +
                "                </div>\n" +
                "                <div>\n" +
                "                    <div>Email :<b>"+profile.email+"</b></div>\n" +
                "                </div>\n" +
                "            </div>\n" );
        if(profile.image!=null && !profile.image.isEmpty()) {
            web_page.append("<div>\n" +
                    "                <img src=\"" + profile.image + "\">\n" +
                    "            </div>\n");
        }
                         web_page.append("</div>\n" +
                "        <div class=\"lower\">\n");
        if(profile.objective!=null && !profile.objective.isEmpty()) {
            web_page.append("<div class=\"heading\">Objective</div><hr>\n" +
                    "            <div class=\"inner\">" + profile.objective + "</div>\n");
        }
        if(profile.intrest!=null && !profile.intrest.isEmpty()) {
            web_page.append("<div class=\"heading\">Field of Interest</div><hr>\n" +
                    "            <div class=\"inner\">"+profile.intrest+"</div>\n");
        }
        if(profile.experience!=null && !profile.experience.isEmpty()) {
            web_page.append("<div class=\"heading\">Experience</div><hr>\n" +
                    "            <ul>\n");
            for (Profile.ExperienceDetails i : profile.experience) {
                web_page.append("<li>\n" +
                        "                    <b>"+i.job+" : </b><i>"+i.start+" - "+i.end+"</i><br>\n" +
                        "                    <b>"+i.company+"</b><br>\n" +
                        "                    <span>"+i.details+"</span>\n" +
                        "                </li>\n");
            }
            web_page.append("</ul>\n");
        }
        if(profile.project!=null && !profile.project.isEmpty()) {
            web_page.append("<div class=\"heading\">Projects</div><hr>\n" +
                    "            <ul>\n");
            for (Profile.ProjectDetails i : profile.project) {
                web_page.append("<li>\n" +
                        "                    <b>"+i.project+"</b><br>\n" +
                        "                    <span>&nbsp "+i.description+"</span>\n" +
                        "                </li>\n");
            }
            web_page.append("</ul>\n");
        }
        if(profile.education!=null && !profile.education.isEmpty()) {
            web_page.append("<div class=\"heading\">Education</div><hr>\n" +
                    "            <ul>\n");
            for (Profile.EducationDetails i : profile.education) {
                web_page.append("<li>\n" +
                        "                    <b>"+i.degree+" - "+i.score+"</b><br>\n" +
                        "                    <i>"+i.school+" - "+i.university+" ("+i.year+")</i>\n" +
                        "               </li>\n");
            }
            web_page.append("</ul>\n");
        }
        if(profile.skills!=null && !profile.skills.isEmpty()) {
            web_page.append("<div class=\"heading\">Skills</div><hr>\n" +
                    "            <ul style=\"display:grid; grid-template-columns:50% 50%;\">\n");
            for (Profile.SkillDetails i : profile.skills) {
                web_page.append("<li>"+i.skill+" - "+this.level(Integer.parseInt(i.level))+"</li>");
            }
            web_page.append("</ul>\n");
        }
        if(profile.strength!=null && !profile.strength.isEmpty()) {
            web_page.append("<div class=\"heading\">Strength</div><hr>\n" +
                    "            <div class=\"inner\">"+profile.strength+"</div>\n");
        }
        if(profile.hobbies!=null && !profile.hobbies.isEmpty()) {
            web_page.append("<div class=\"heading\">Hobbies</div><hr>\n" +
                    "            <div class=\"inner\">"+profile.hobbies+"</div>\n");
        }
                        web_page.append("</div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>");
        return web_page.toString();
    }
    String level(int num){
        if(num>=85) return "Professional";
        else if(num>=75) return "Advanced";
        else if(num>=60) return "Intermediate";
        else if(num>=30) return "Beginner";
        return "Basic";
    }
}
