package com.type_moon.codeflame.fatedictionary.Tool;


public class SpinnerSelect {
    public SpinnerSelect(){
    }

    public static int getLevel(String string) {
        switch (string){
            case "E":
                return 0;
            case "D-":
                return 1;
            case "D":
                return 2;
            case "D+":
                return 3;
            case "C-":
                return 4;
            case "C":
                return 5;
            case "C+":
                return 6;
            case "B-":
                return 7;
            case "B":
                return 8;
            case "B+":
                return 9;
            case "B++":
                return 10;
            case "A-":
                return 11;
            case "A":
                return 12;
            case "A+":
                return 13;
            case "A++":
                return 14;
            case "A+++":
                return 15;
            case "EX":
                return 16;
            case "-":
                return 17;
            case "？":
                return 18;
            default:
                return 18;
        }
    }

    public static int getType(String string) {
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
