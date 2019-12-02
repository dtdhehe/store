package com.example.store.util;

import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 罗蕾
 * @version 1.0.0
 * @date 2019/11/6 18:06
 * @description
 **/
public class DateUtils {

    public static Date now() {
        return new Date();
    }

    public static String formatDate(Date date, String pattern) {
        if (date == null) {
            return "";
        } else {
            if (pattern == null) {
                pattern = "yyyy-MM-dd";
            }

            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
        }
    }

    public static String formatDateTime() {
        return formatDate(now(), "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatDateTime2() {
        return formatDate(now(), "yyyyMMddHHmmssSSS");
    }

    public static String formatDateTime3() {
        return formatDate(now(), "yyyyMMddHHmmss");
    }

    /**
     * 将前台yyyy-MM-dd HH:mm:ss或yyyy-MM-dd 转成 yyyyMMddHHmmss 或yyyyMMdd
     *
     * @author Wayne
     * @date 2018/7/25 9:45
     * @param[date]
     * @return java.lang.String
     */
    public static String convertDateToDBType(String date){
        String formatDate = "";
        if(StringUtils.isEmpty(date)){
            return date;
        }
        formatDate = date.replaceAll("\\s|-|:","");
        return formatDate;
    }

    /**
     * 将后台 yyyyMMddHHmmss 或yyyyMMdd 转成 yyyy-MM-dd HH:mm:ss或yyyy-MM-dd
     *
     * @author Wayne
     * @date 2018/7/25 10:24
     * @param[date]
     * @return java.lang.String
     */
    public static String convertDateToViewType(String date,String dateType){
        String formateDate="";
        if(StringUtils.isEmpty(date)){
            return date;
        }
        if("datetime".equals(dateType)){
            formateDate =  date.substring(0,4)+"-"
                    +date.substring(4,6)+"-"
                    +date.substring(6,8)+" "
                    +date.substring(8,10)+":"
                    +date.substring(10,12)+":"
                    +date.substring(12,14);
            return formateDate;
        }else if("date".equals(dateType)){
            formateDate =  date.substring(0,4)+"-"
                    +date.substring(4,6)+"-"
                    +date.substring(6,8);
            return formateDate;
        }
        return date;
    }

    /**
     * 获取当前年月日,时分秒毫秒
     * 格式 2018101015000000   16位
     * @return
     */
    public static String getCurrentDateTime(){
        Date now = new Date();
        //获取当前年月日
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:mm");
        String currentDate = dateFormat.format(now);
        String time = currentDate.replace("/","");
        String currentTime = time.replace(" ","");
        return currentTime.replace(":","");
    }

}
