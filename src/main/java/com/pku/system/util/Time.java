package com.pku.system.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {
    public String getCurrentTime(){
        //获取当前系统时间
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyyMMddHHmmssSSSS");
        String time=format.format(date);
        return time;
    }
}
