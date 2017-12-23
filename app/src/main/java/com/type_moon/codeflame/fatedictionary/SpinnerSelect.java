package com.type_moon.codeflame.fatedictionary;


public class SpinnerSelect {
    public SpinnerSelect(){
    }

    static int getJobSelect(String string) {
        switch (string){
            case "saber":
                return 0;
            case "archer":
                return 1;
            case "lancer":
                return 2;
            case "rider":
                return 3;
            case "caster":
                return 4;
            case "assassin":
                return 5;
            case "berserker":
                return 6;
            case "ruler":
                return 7;
            case "avenger":
                return 8;
            default:
                return 0;
        }
    }

    static int getSex(String string) {
        switch (string){
            case "男":
                return 0;
            case "女":
                return 1;
            default:
                return 0;
        }
    }

    static int getLevel(String string) {
        switch (string){
            case "E":
                return 0;
            case "D":
                return 1;
            case "D+":
                return 2;
            case "C":
                return 3;
            case "C+":
                return 4;
            case "B-":
                return 5;
            case "B":
                return 6;
            case "B+":
                return 7;
            case "A-":
                return 8;
            case "A":
                return 9;
            case "A+":
                return 10;
            case "A++":
                return 11;
            case "A+++":
                return 12;
            case "EX":
                return 13;
            case "-":
                return 14;
            default:
                return 0;
        }
    }

    static int getAlignmengt(String string) {
        switch (string){
            case "秩序·善":
                return 0;
            case "中立·善":
                return 1;
            case "混沌·善":
                return 2;
            case "秩序·中庸":
                return 3;
            case "中立·中庸":
                return 4;
            case "混沌·中庸":
                return 5;
            case "秩序·恶":
                return 6;
            case "中立·恶":
                return 7;
            case "混沌·恶":
                return 8;
            default:
                return 0;
        }
    }

    static int getType(String string) {
        switch (string){
            case "固有技能":
                return 0;
            case "职阶技能":
                return 1;
            case "宝具":
                return 2;
            default:
                return 0;
        }
    }

}
