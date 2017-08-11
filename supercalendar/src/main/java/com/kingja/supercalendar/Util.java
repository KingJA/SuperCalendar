package com.kingja.supercalendar;

import android.content.Context;
import android.util.TypedValue;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description:TODO
 * Create Time:2017/8/10 11:20
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class Util {

    public static String getFormatDate(String format, Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static boolean isSameMonth(Date dateA, Date dateB) {
        return dateA.getMonth() == dateB.getMonth();
    }

    public static boolean isToday(Date date) {
        Date nowDate = new Date();
        return date.getYear() == nowDate.getYear() && date.getMonth
                () == nowDate.getMonth() && date.getDate() == nowDate.getDate();
    }

    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources()
                .getDisplayMetrics());


    }
}
