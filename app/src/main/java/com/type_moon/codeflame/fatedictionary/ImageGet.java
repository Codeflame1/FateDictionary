package com.type_moon.codeflame.fatedictionary;


public class ImageGet {
    public ImageGet(){

    }
    static int getBigFrame(String string){
        switch (string){
            case "saber":
                return R.mipmap.saberbig;
            case "archer":
                return R.mipmap.archerbig;
            case "lancer":
                return R.mipmap.lancerbig;
            case "rider":
                return R.mipmap.riderbig;
            case "caster":
                return R.mipmap.casterbig;
            case "assassin":
                return R.mipmap.assassinbig;
            case "berserker":
                return R.mipmap.berserkerbig;
            case "ruler":
                return R.mipmap.rulerbig;
            case "avanger":
                return R.mipmap.avangerbig;
        }
        return 0;
    }

    static int getSmallFrame(String string){
        switch (string){
            case "saber":
                return R.mipmap.saber;
            case "archer":
                return R.mipmap.archer;
            case "lancer":
                return R.mipmap.lancer;
            case "rider":
                return R.mipmap.rider;
            case "caster":
                return R.mipmap.caster;
            case "assassin":
                return R.mipmap.assassin;
            case "berserker":
                return R.mipmap.berserker;
            case "ruler":
                return R.mipmap.ruler;
            case "avanger":
                return R.mipmap.avenger;
        }
        return 0;
    }

    static int getLevelImage(String string){
        switch (string){
            case "E":
                return R.mipmap.levele;
            case "D":
                return R.mipmap.leveld;
            case "D+":
                return R.mipmap.leveld;
            case "C":
                return R.mipmap.levelc;
            case "C+":
                return R.mipmap.levelc;
            case "B":
                return R.mipmap.levelb;
            case "B+":
                return R.mipmap.levelb;
            case "A":
                return R.mipmap.levela;
            case "A+":
                return R.mipmap.levela;
            case "A++":
                return R.mipmap.levela;
            case "A+++":
                return R.mipmap.levela;
            case "EX":
                return R.mipmap.levelex;
            case "-":
                return R.mipmap.levelnull;
        }
        return 0;
    }
}
