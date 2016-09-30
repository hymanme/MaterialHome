package com.hymane.materialhome.utils.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/1
 * Description:
 */
public class TimeUtils {
    public static String getTimeFromStamp(long timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date(timeStamp));
    }

    public static String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(Calendar.getInstance().getTime());
    }

    public static long getCurrentTimeStamp() {
        return System.currentTimeMillis();
    }

    public static String parseTime(String complexTime) {
        return complexTime;
    }
}
