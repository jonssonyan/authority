package com.jonsson.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    /**
     * 获取指定日期的开始时间
     *
     * @param date
     * @return
     */
    public static Date getStartDate(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.set(Calendar.HOUR_OF_DAY, 0);
        instance.set(Calendar.MINUTE, 0);
        instance.set(Calendar.SECOND, 0);
        return instance.getTime();
    }

    /**
     * 获取指定日期的结束时间
     *
     * @param date
     * @return
     */
    public static Date getEndDate(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.set(Calendar.HOUR_OF_DAY, 23);
        instance.set(Calendar.MINUTE, 59);
        instance.set(Calendar.SECOND, 59);
        return instance.getTime();
    }

    /**
     * 获取过去或者未来任意天内的日期数组
     *
     * @param intervals
     * @param flag
     * @return
     */
    public static ArrayList<Date> getDayList(Integer intervals, Boolean flag) {
        if (flag) {
            ArrayList<Date> pastDaysList = new ArrayList<>();
            for (int i = 0; i < intervals; i++) {
                pastDaysList.add(getPastDate(i));
            }
            return pastDaysList;
        } else {
            ArrayList<Date> futureDaysList = new ArrayList<>();
            for (int i = 0; i < intervals; i++) {
                futureDaysList.add(getFutureDate(i));
            }
            return futureDaysList;
        }
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static Date getPastDate(Integer past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        return calendar.getTime();
    }

    /**
     * 获取未来第几天的日期
     *
     * @param past
     * @return
     */
    public static Date getFutureDate(Integer past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        return calendar.getTime();
    }
}