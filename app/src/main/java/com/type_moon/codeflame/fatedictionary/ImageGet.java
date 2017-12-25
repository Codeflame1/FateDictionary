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
            case "avenger":
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
            case "avenger":
                return R.mipmap.avenger;
        }
        return 0;
    }

    static int getLevelImage(String string){
        if (string.equals("E")) {
            return R.mipmap.levele;
        } else if (string.contains("D")) {
            return R.mipmap.leveld;
        } else if (string.contains("C")) {
            return R.mipmap.levelc;
        } else if (string.contains("B")) {
            return R.mipmap.levelb;
        } else if (string.contains("A")) {
            return R.mipmap.levela;
        } else if (string.equals("EX")) {
            return R.mipmap.levelex;
        } else if (string.equals("-")) {
            return R.mipmap.levelnull;
        } else {
            return R.mipmap.levelnull;
        }
    }
}
