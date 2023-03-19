package com.example.resumecraft;

import android.util.Log;

import com.example.resumecraft.templates.Template_1;
import com.example.resumecraft.templates.Template_2;
import com.example.resumecraft.templates.Template_3;

public class Templates {
    public int img , name;
    Templates(int img , int name){
        this.img=img;
        this.name=name;
    }
    public static String build(int t , Profile profile){
        switch (t) {
            case 1: {
                Template_1 obj = new Template_1();
                return obj.get(profile);
            }
            case 2: {
                Template_2 obj = new Template_2();
                return obj.get(profile);
            }
            case 3:{
                Template_3 obj = new Template_3();
                return obj.get(profile);
            }
            default:
                return "";
        }
    }
}
