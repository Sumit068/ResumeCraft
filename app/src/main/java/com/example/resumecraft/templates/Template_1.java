package com.example.resumecraft.templates;

import android.util.Log;

import com.example.resumecraft.Profile;

public class Template_1 {
    public String get(Profile profile) {
        StringBuilder web_page = new StringBuilder();
        web_page.append("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <script src=\"https://kit.fontawesome.com/3581be3774.js\" crossorigin=\"anonymous\"></script>\n" +
                "    </style>\n" +
                "    <style>\n" +
                "        body{\n" +
                "            margin:0;\n" +
                "        }        \n" +
                "        .container{\n" +
                "            display:grid;\n" +
                "            width: 210mm;\n" +
                "            min-height:296.8mm;\n" +
                "            grid-template-columns: 30% auto;\n" +
                "            background: white;\n" +
                "        }        \n" +
                "        img{\n" +
                "            width: 100%;\n" +
                "            height: 35mm;\n" +
                "        }\n" +
                "        .left-col{\n" +
                "            background-color:mistyrose;\n" +
                "        }\n" +
                "        .right-col{\n" +
                "            padding: 1% 2% 1% 2%;\n" +
                "        }\n" +
                "        .personal-details{\n" +
                "            font-size: 14px;\n" +
                "            color:slategrey;  \n" +
                "            padding-left: 1%;  \n" +
                "            word-wrap: break-word; \n" +
                "        }\n" +
                "        .section-head{\n" +
                "            font-size: 18px; \n" +
                "            color:dimgray;\n" +
                "        }\n" +
                "        .details{\n" +
                "            padding:0 3mm 0 5mm;\n" +
                "            font-size:14px;\n" +
                "            color:black;\n" +
                "        }\n" +
                "        .head-details{\n" +
                "            font-size:16px;\n" +
                "        }\n" +
                "        .skill-bg{\n" +
                "            display: block;\n" +
                "            background-color: #f1f1f1;\n" +
                "            width : 100%;\n" +
                "            border-radius: 20px;\n" +
                "            margin-top: 1mm;\n" +
                "        }\n" +
                "        .skill-level{\n" +
                "            display:block;\n" +
                "            background-color: #009688;\n" +
                "            text-align:center;\n" +
                "            border-radius: 20px;\n" +
                "        }\n" +
                "        .icons-style{\n" +
                "            color:#009688;\n" +
                "            font-size:12px;\n" +
                "            margin-right: 1%;\n" +
                "        }\n" +
                "        ul{\n" +
                "            margin:0;\n" +
                "            padding-left: 15px;\n" +
                "        }\n" +
                "        }    </style>\n" +
                "    <script src=\"https://kit.fontawesome.com/a076d05399.js\" crossorigin=\"anonymous\"></script>\n" +
                "    <title>Document</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"left-col\">\n");
        if(profile.image!=null && !profile.image.isEmpty()) {
            web_page.append("<img src=\"" + profile.image + "\">\n");
        }
                web_page.append("<div style=\"padding-left:2%\">\n" +
                "                <div style=\"font-size: 18px; text-align: center;\"><b>" + profile.name + "</b></div>\n" +
                "                <div class=\"personal-details\">\n" +
                "                    <i class=\"fa-solid fa-house-chimney icons-style\"></i>\n" +
                profile.address + "<br>\n" +
                "                    <i class=\"fa-solid fa-envelope icons-style\"></i>\n" +
                profile.email + "<br>\n" +
                "                    <i class=\"fa-solid fa-phone icons-style\"></i>\n" +
                profile.number + "\n" +
                "                </div>");
        if (profile.objective != null && !profile.objective.isEmpty()) {
            web_page.append("<hr size=\"2\" color=\"gainsboro\">\n" +
                    "                <div class=\"section-head\" style=\"padding-left:1%\">\n" +
                    "                    <i class=\"fa-solid fa-crosshairs icons-style\" style=\"font-size:18px\"></i>\n" +
                    "                    Objective\n" +
                    "                    <div class=\"details\" style=\"padding-left:2%\"> "+profile.objective+"\n" +
                    "                    </div>\n" +
                    "                </div>\n");
        }
        if (profile.skills != null && !profile.skills.isEmpty()) {
            web_page.append("<hr size=\"2\" color=\"gainsboro\">\n" +
                    "                <div class=\"section-head\" style=\"padding-left:1%\">\n" +
                    "                    <i class=\"fa-solid fa-certificate icons-style\" style=\"font-size:18px\"></i>\n" +
                    "                    Skills\n");
            for (Profile.SkillDetails i : profile.skills) {
                web_page.append("<div class=\"details\" style=\"padding-left:2%;\">\n" +
                        i.skill +
                        "                        <div class=\"skill-bg\">\n" +
                        "                            <div class=\"skill-level\" style=\"width:" + i.level + "%;\">" + i.level + "%</div>\n" +
                        "                        </div>\n" +
                        "                    </div>\n");
            }
            web_page.append("</div>\n");
        }
        if (profile.strength != null && !profile.strength.isEmpty()) {
            web_page.append("<hr size=\"2\" color=\"gainsboro\">\n" +
                    "                <div class=\"section-head\" style=\"padding-left:1%\">\n" +
                    "                    <i class=\"fa-solid fa-hand-fist icons-style\" style=\"font-size:18px\"></i>\n" +
                    "                    Strength\n" +
                    "                    <div class=\"details\" style=\"padding-left:2%\">" + profile.strength +
                    "                    </div>\n" +
                    "                </div>\n");
        }
        if (profile.hobbies != null && !profile.hobbies.isEmpty()) {
            web_page.append("<hr size=\"2\" color=\"gainsboro\">\n" +
                    "                <div class=\"section-head\" style=\"padding-left:1%\">\n" +
                    "                    <i class=\"fa-solid fa-headphones-simple icons-style\" style=\"font-size:18px\"></i>\n" +
                    "                    Hobbies\n" +
                    "                    <div class=\"details\" style=\"padding-left:2%\">" + profile.hobbies +
                    "                    </div>\n" +
                    "                </div>\n");
        }
        web_page.append("</div>        \n" +
                "        </div>" +
                "        <div class=\"right-col\">");
        if (profile.intrest != null && !profile.intrest.isEmpty()) {
            web_page.append("<div class=\"section-head\">\n" +
                    "                <i class=\"fa-solid fa-lightbulb icons-style\" style=\"font-size:18px\"></i>\n" +
                    "                Field of Interest\n" +
                    "                <div class=\"details\">\n" +
                    "                    <hr size=\"1\" color=\"gainsboro\">\n" +
                    "                    <div>" + profile.intrest + "</div>\n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "            <hr size=\"2\" color=\"darkgray\" style=\"margin-bottom:2%;\">\n");
        }
        if (profile.experience != null && !profile.experience.isEmpty()) {
            web_page.append("<div class=\"section-head\">\n" +
                    "                <i class=\"fa-solid fa-suitcase icons-style\" style=\"font-size:18px;\"></i>\n" +
                    "                Work Experience\n");
            for (Profile.ExperienceDetails i : profile.experience) {
                web_page.append("<div class=\"details\">\n" +
                        "                    <hr size=\"1\" color=\"gainsboro\">\n" +
                        "                    <div class=\"head-details\">" + i.job + " / " + i.company + "</div>\n" +
                        "                    <div>\n" +
                        "                        <i class=\"fa-solid fa-calendar-days icons-style\"></i>\n" +
                        i.start + " - " + i.end + "\n" +
                        "                    </div>\n" +
                        "                    <div>" + i.details + "</div>\n" +
                        "                </div>\n");
            }
            web_page.append("</div>\n" +
                    "            <hr size=\"2\" color=\"darkgray\" style=\"margin-bottom:2%;\">\n");
        }
        if (profile.education != null && !profile.education.isEmpty()) {
            web_page.append("<div class=\"section-head\">\n" +
                    "                <i class=\"fa-solid fa-graduation-cap icons-style\" style=\"font-size:18px;\"></i>\n" +
                    "                Education\n");
            for (Profile.EducationDetails i : profile.education) {
                web_page.append("<div class=\"details\">\n" +
                        "                    <hr size=\"1\" color=\"gainsboro\">\n" +
                        "                    <div class=\"head-details\">" + i.school + " / " + i.university + "</div>\n" +
                        "                    <div>\n" +
                        "                        <i class=\"fa-solid fa-calendar-days icons-style\"></i>\n" +
                        i.year + "\n" +
                        "                    </div>\n" +
                        "                    <div>" + i.degree + "(" + i.score + ")</div>\n" +
                        "                </div>\n");
            }
            web_page.append("</div>\n" +
                    "            <hr size=\"2\" color=\"darkgray\" style=\"margin-bottom:2%;\">\n");
        }
        if (profile.project != null && !profile.project.isEmpty()) {
            web_page.append("<div class=\"section-head\">\n" +
                    "                <i class=\"fa-solid fa-rocket icons-style\" style=\"font-size:18px\"></i>\n" +
                    "                Project\n");
            for (Profile.ProjectDetails i : profile.project) {
                web_page.append("<div class=\"details\">\n" +
                        "                    <hr size=\"1\" color=\"gainsboro\">\n" +
                        "                    <div class=\"head-details\">"+i.project +"</div>\n" +
                        "                    <div>" + i.description + "</div>\n" +
                        "                </div>\n");
            }
            web_page.append("</div>\n");
        }
        web_page.append("</div>\n" +
                "\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>");
        return web_page.toString();
    }
}
