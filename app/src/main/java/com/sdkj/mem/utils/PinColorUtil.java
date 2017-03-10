package com.sdkj.mem.utils;


import com.sdkj.mem.bean.PinColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2016-12-29.
 *  26 字母对应颜色
 * @author KittyKang
 */
public class PinColorUtil {

    public static  String[] mSideLetter  = { "#","A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z"};

    public static  String[] mColors  = { "#48bed9","#f78c65", "#f5a952", "#88bf41", "#6c9ce0", "#6c9ce0", "#986abf",
            "#48bed9","#f78c65", "#f5a952", "#88bf41", "#6c9ce0", "#6c9ce0", "#986abf",
            "#48bed9","#f78c65", "#f5a952", "#88bf41", "#6c9ce0", "#6c9ce0", "#986abf",
            "#48bed9","#f78c65", "#f5a952", "#88bf41", "#6c9ce0", "#6c9ce0", "#986abf"};

    public static List<PinColor> addPinColor(){
        List<PinColor> pinColors = new ArrayList<PinColor>();
        for (int i = 0;i<mSideLetter.length;i++){
            PinColor pinColor = new PinColor();
            pinColor.letter = mSideLetter[i];
            pinColor.color = mColors[i];
            pinColors.add(pinColor);
        }
        return  pinColors;
    }

    //查找某个字母对应的颜色

    public static String getPinColor(String letter,List<PinColor> pinList){
        String color = mColors[0];
        for(int i = 0;i<pinList.size();i++){
            PinColor pinColor = pinList.get(i);
            if(pinColor.letter.equals(letter)){
                color = pinColor.color;
                break;
            }
        }
        return color;
    }

}
