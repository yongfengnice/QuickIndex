package com.quickindex.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Creator: syf(2499522170@qq.com)
 * Date   : on 2016/11/14 0014
 * Desc   : 索引条工具类
 */
public class BarUtils {
    public static final String sChar_z = "z";
    public static final String sFirstChar = "↑";
    public static final String sLastChar = "#";

    // Math.round()
    public static float pxFromSp(Context context, float spSize) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spSize, context.getResources().getDisplayMetrics());
    }
}
