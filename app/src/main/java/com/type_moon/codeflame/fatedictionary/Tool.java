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
}
