package com.geetest.onelogin;

import android.graphics.Color;
import android.text.TextUtils;

public class ColorTools {
    private ColorTools() {
 
    }
 
    /**
     * 解析颜色
     * @param colorStr
     * @return
     */
    public static int getColor(String colorStr) {
        int color;
        try {
            if(colorStr != null && !colorStr.startsWith("#")){
                colorStr = "#" + colorStr;
            }
            color = Color.parseColor(colorStr);
        }catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return color;
    }
 
    /**
     * 解析颜色
     * @param colorStr
     * @param defaultColor
     * @return
     */
    public static int getColor(String colorStr, String defaultColor) {
        int color = Color.WHITE;
        try {
            if (TextUtils.isEmpty(colorStr) && !TextUtils.isEmpty(defaultColor)) {
                color = Color.parseColor(defaultColor);
            }else {
                if(colorStr != null && !colorStr.startsWith("#")){
                    colorStr = "#" + colorStr;
                }
                color = Color.parseColor(colorStr);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return color;
    }
}