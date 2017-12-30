package com.type_moon.codeflame.fatedictionary.Tool;


import com.type_moon.codeflame.fatedictionary.R;

public class ImageGet {
    public ImageGet(){

    }
    public static int getBigFrame(String string){
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
            case "shielder":
                return R.mipmap.shielderbig;
            case "none":
                return R.mipmap.nonebig;
        }
        return 0;
    }

    public static int getSmallFrame(String string){
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
            case "shielder":
                return R.mipmap.shielder;
            case "none":
                return R.mipmap.none;
        }
        return 0;
    }

    public static int getLevelImage(String string){
        if (string.equals("EX")) {
            return R.mipmap.levelex;
        } else if (string.contains("D")) {
            return R.mipmap.leveld;
        } else if (string.contains("C")) {
            return R.mipmap.levelc;
        } else if (string.contains("B")) {
            return R.mipmap.levelb;
        } else if (string.contains("A")) {
            return R.mipmap.levela;
        } else if (string.contains("E")&&!string.equals("EX")) {
            return R.mipmap.levele;
        } else {
            return R.mipmap.levelnull;
        }
    }
}
