package com.type_moon.codeflame.fatedictionary;


import java.text.DecimalFormat;

public class Tool {
    public Tool() {
    }

    static String numDecimal (int i) {
        DecimalFormat df=new DecimalFormat("000");
        if (i>0&&i<1000) {
            return df.format(i);
        }
        else {
            return i + "";
        }
    }
    static String getJob(int i) {
        switch (i){
            case 0:
                return "saber";
            case 1:
                return "archer";
            case 2:
                return "lancer";
            case 3:
                return "rider";
            case 4:
                return "caster";
            case 5:
                return "assassin";
            case 6:
                return "berserker";
            case 7:
                return "ruler";
            case 8:
                return "avenger";
            default:
                return "saber";
        }
    }
    static String getSex(int i) {
        switch (i){
            case 0:
                return "男";
            case 1:
                return "女";
            default:
                return "女";
        }
    }

    static String getAlignment(int i) {
        switch (i){
            case 0:
                return "秩序·善";
            case 1:
                return "中立·善";
            case 2:
                return "混沌·善";
            case 3:
                return "秩序·中庸";
            case 4:
                return "中立·中庸";
            case 5:
                return "混沌·中庸";
            case 6:
                return "秩序·恶";
            case 7:
                return "中立·恶";
            case 8:
                return "混沌·恶";
            default:
                return "秩序·善";
        }
    }
}