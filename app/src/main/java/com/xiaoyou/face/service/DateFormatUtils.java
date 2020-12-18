package com.xiaoyou.face.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author lenyuqin
 * @data 2020/12/18
 */
public class DateFormatUtils {


    /**
     *
     * @return 返回当前时间的字符串
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getTodayDate() {
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return  dateTimeFormatter.format(time);
    }
}
