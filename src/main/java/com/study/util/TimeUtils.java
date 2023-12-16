package com.study.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class TimeUtils {

    /**
     * 一分钟
     */
    public final static int MINUTE = 60 * 1000;

    /**
     * 一小时
     */
    public final static int HOUR = 60 * MINUTE;
    /**
     * 一天
     */
    public final static int DAY = 24 * HOUR;

    /**
     * 一月
     */
    public final static long MONTH = 31L * DAY;

    /**
     * 一年
     */
    public final static long YEAR = 12 * MONTH;



    public static int getCurSecond() {
        return (int) (currentTimeMillis() / 1000);
    }

    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static int getDateSecond(Date date) {
        return (int)(date.getTime() / 1000);
    }

    public static int getSecondDiff(int start) {
        return (int) (currentTimeMillis() / 1000 - start);
    }

    public static boolean belongCalendar(Date nowTime, Date beginTime,
                                         Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static Integer getDateTimeFormat(String date) {
        try {
            return ((Long)(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date).getTime()/1000)).intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * yyyy-MM-dd
     * @param date
     * @return
     */
    public static Integer getDateFormat(String date) {
        try {
            return ((Long)(new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime()/1000)).intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * yyyy-MM-dd
     * @param date
     * @return
     */
    public static Integer getDateFormat2(String date) {
        try {
            return ((Long)(new SimpleDateFormat("yyyyMMdd").parse(date).getTime()/1000)).intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 时间转换 (中文)
     *
     * 转换格式：
     *  中文            英文
     * 一分钟前：     1 minute ago
     * 几分钟前 ：    27 minutes ago
     * 几小时前：     3 hours ago
     * 1天前：       1 day ago
     * 7天前：       7 day ago
     * 一个月前：    1 month ago
     * 三个月前：    3 month ago
     * 一年前:      1 years ago
     *
     * @param date 需要转化的时间
     * @return 转换后的格式
     */
    public static String formatTimeZh(Date date) {
        List<String> dateTimes = new ArrayList<>();

        Assert.notNull(date,"date:null");
        long diff = System.currentTimeMillis() - date.getTime();
        long r = 0;
        if (diff > YEAR) {
            r = (diff / YEAR);
            dateTimes.add(r + "年前");
        }
        if (diff > MONTH) {
            r = (diff / MONTH);
            dateTimes.add(r + "个月前");
        }
        if (diff > DAY) {
            r = (diff / DAY);
            dateTimes.add(r + "天前");
        }
        if (diff > HOUR) {
            r = (diff / HOUR);
            dateTimes.add(r + "个小时前");
        }
        if (diff > MINUTE) {
            r = (diff / MINUTE);
            dateTimes.add(r + "分钟前");
        } else {
            dateTimes.add("刚刚");
        }

        if (dateTimes.contains("刚刚")) {
            return "刚刚";
        }
        for (String dateTime : dateTimes) {
            if (dateTime.contains("年前")) {
                return dateTime;
            }
            if (dateTime.contains("个月前")) {
                return dateTime;
            }
            if (dateTime.contains("天前")) {
                return dateTime;
            }
            if (dateTime.contains("个小时前")) {
                return dateTime;
            }
            if (dateTime.contains("分钟前")) {
                return dateTime;
            }
        }
        return null;
    }

    /**
     * 时间转换 (英文)
     *
     * 转换格式：
     * 一分钟前：     1 minute ago
     * 几分钟前 ：    27 minutes ago
     * 几小时前：     3 hours ago
     * 1天前：       1 day ago
     * 7天前：       7 day ago
     * 一个月前：    1 month ago
     * 三个月前：    3 month ago
     * 一年前:      1 years ago
     *
     * @param date 需要转化的时间
     * @return 转换后的格式
     */
    public static String formatTimeUs(Date date) {
        List<String> dateTimes = new ArrayList<>();

        Assert.notNull(date,"date:null");
        long diff = System.currentTimeMillis() - date.getTime();
        long r = 0;
        if (diff > YEAR) {
            r = (diff / YEAR);
            dateTimes.add(r + " years ago");
        }
        if (diff > MONTH) {
            r = (diff / MONTH);
            dateTimes.add(r + " month ago");
        }
        if (diff > DAY) {
            r = (diff / DAY);
            dateTimes.add(r + " day ago");
        }
        if (diff > HOUR) {
            r = (diff / HOUR);
            dateTimes.add(r + " hours ago");
        }
        if (diff > MINUTE) {
            r = (diff / MINUTE);
            dateTimes.add(r + " minutes ago");
        } else {
            dateTimes.add("just now");
        }

        if (dateTimes.contains("just now")) {
            return "just now";
        }
        for (String dateTime : dateTimes) {
            if (dateTime.contains("years ago")) {
                return dateTime;
            }
            if (dateTime.contains("month ago")) {
                return dateTime;
            }
            if (dateTime.contains("day ago")) {
                return dateTime;
            }
            if (dateTime.contains("hours ago")) {
                return dateTime;
            }
            if (dateTime.contains("minutes ago")) {
                return dateTime;
            }
        }
        return null;
    }

    public static Date toDate(Long timeStamp) {
        return timeStamp.toString().length() == 10 ? Convert.toDate(timeStamp * 1000L) : Convert.toDate(timeStamp);
    }

    public static Date toDate(Integer timeStamp) {
        return Convert.toDate(timeStamp * 1000L);
    }

    /**
     * 根据指定的时间戳，判断当天是周几，返回的数字与Calendar对应的周几一致
     * 如果参数timeStamp为null，则取当前时间
     * 如：1：周日
     * @param timeStamp 时间戳，不包含毫秒
     * @return Calendar类对应的周几的定义
     */
    public static int getDayOfWeek(Integer timeStamp){
        if (timeStamp == null) {
            timeStamp = getCurSecond();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeStamp * 1000L);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 根据指定的时间戳，返回当天是一个月的第几日
     * 如果参数timeStamp为null，则取当前时间
     * @param timeStamp 时间戳，不包含毫秒
     * @return Calendar类对应的周几的定义
     */
    public static int getDayOfMonth(Integer timeStamp){
        if (timeStamp == null) {
            timeStamp = getCurSecond();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeStamp * 1000L);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前月的第一天，比如今天是北京时间2023-09-04，则获取2023-09-01 07:00:00的时间戳
     * @return 尼日利亚当月第一天零点的时间戳
     */
    public static int getCurMonthFirstDay(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 7);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (int) (cal.getTimeInMillis() / 1000);
    }

    public static int getCurYearFirstDay(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 7);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (int) (cal.getTimeInMillis() / 1000);
    }

    /**
     * 根据指定的时间戳，判断当天是不是周一
     * @param timeStamp 时间戳，不包含毫秒
     * @return true代表周一
     */
    public static boolean isMonday(Integer timeStamp){
        return Calendar.MONDAY == getDayOfWeek(timeStamp);
    }

    /**
     * 根据指定的时间戳，判断当天是不是当月第一天
     * @param timeStamp 时间戳，不包含毫秒
     * @return true代表是当月第一天
     */
    public static boolean isMonthFirstDay(Integer timeStamp){
        return 1 == getDayOfMonth(timeStamp);
    }

    public static boolean isSeasonFirstDay(Integer timeStamp){
        if (timeStamp == null) {
            timeStamp = getCurSecond();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeStamp * 1000L);
        return cal.get(Calendar.MONTH) % 3 == 0 && cal.get(Calendar.DAY_OF_MONTH) == 1;
    }

    /**
     * 根据指定的时间戳，判断当天是不是该年份的第一天
     * @param timeStamp 时间戳，不包含毫秒
     * @return true代表是该年份的第一天
     */
    public static boolean isYearFirstDay(Integer timeStamp){
        if (timeStamp == null) {
            timeStamp = getCurSecond();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeStamp * 1000L);
        return cal.get(Calendar.MONTH) == Calendar.JANUARY && cal.get(Calendar.DAY_OF_MONTH) == 1;
    }


    /**
     * 比较2个 时间差
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 时间间隔
     */
    public static Duration between(Date startDate, Date endDate) {
        return Duration.between(startDate.toInstant(), endDate.toInstant());
    }

    /**
     * 获取本周开始结束时间，周一到周日
     * @return 开始结束
     */
    public static int[] getCurrentWeekTimeStartAndEnd() {
        Calendar calendar = Calendar.getInstance();
        // start of the week
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            calendar.add(Calendar.DAY_OF_YEAR,-1);
        }
        calendar.add(Calendar.DAY_OF_WEEK, -(calendar.get(Calendar.DAY_OF_WEEK) - 2));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        int startTime = (int) (calendar.getTimeInMillis() / 1000);
        // end of the week
        calendar.add(Calendar.DAY_OF_WEEK, 6);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        int endTime = (int) (calendar.getTimeInMillis() / 1000);
        return new int[]{startTime, endTime};
    }

    /**
     * 获取当天日期并指定时间的时间戳
     * @param timeStr HH:mm:ss
     * @return 时间戳
     */
    public static int getTodayAndFixedTime(String timeStr) {
        // 获取今天日期yyyy-MM-dd
        return getFixedDayAndFixedTime(DateUtil.today(), timeStr);
    }

    /**
     * 获取当天或昨天日期并8点的时间戳
     * @return 时间戳
     */
    public static int getTodayOrYesterdayEightClock(String timeStr) {
        // 获取今天日期yyyy-MM-dd
        int fixedDayAndFixedTime = getFixedDayAndFixedTime(DateUtil.today(), timeStr);

        if (TimeUtils.getCurSecond() < getFixedDayAndFixedTime(DateUtil.today(), "07:00:00")) {
            fixedDayAndFixedTime = fixedDayAndFixedTime - 24 * 60 * 60;
        }

        return fixedDayAndFixedTime;
    }

    /**
     * 获取指定日期以及指定时间的时间戳
     * @param dateStr yyyy-MM-dd,DateUtil.today()可获取当前yyyy-MM-dd上面的方法
     * @param timeStr HH:mm:ss
     * @return 时间戳
     */
    public static int getFixedDayAndFixedTime(String dateStr, String timeStr) {
        // yyyy-MM-dd HH:mm:ss
        String dateTimeStr = dateStr + " " + timeStr;
        return (int) (DateUtil.parse(dateTimeStr, DatePattern.NORM_DATETIME_PATTERN).getTime() / 1000);
    }

    /**
     * 获取偏移天的时间戳
     * @param time 当前时间戳
     * @param offset 偏移天数
     * @return 指定偏移天的时间戳
     */
    public static int getOffsetFixedTime(int time, int offset) {
        DateTime dateTime = DateUtil.date(time * 1000L);
        return (int) (DateUtil.offsetDay(dateTime, offset).getTime() / 1000);
    }

    /**
     * 返回尼日利亚时间（格式为：EEEE, MMMM dd, yyyy 'at' HH:mm）
     *
     * @param date (豪秒）
     * @return 英文输出尼日利亚时间
     */
    public static String nigeriaDateEn(Long date) {
        Date millisecondDate = new Date(date);
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMMM dd, yyyy 'at' HH:mm", Locale.ENGLISH);
        Calendar cal = Calendar.getInstance();
        //设置起时间
        cal.setTime(millisecondDate);
        cal.add(Calendar.HOUR, -7);
        return formatter.format(cal.getTime());
    }

    /**
     * 返回时间为：YYYY-MM-DD HH:mm:ss格式的字符串
     *
     * @param second (秒）为单位的时间戳
     * @return 时间字符串
     */
    public static String formatDate(Integer second) {
        if (second == null) {
            return null;
        }
        Date millisecondDate = new Date(second * 1000L);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //设置起时间
        return formatter.format(millisecondDate);
    }

    /**
     * 返回时间为：YYYY-MM-DD HH:mm:ss格式的字符串
     *
     * @param second (秒）为单位的时间戳
     * @return 时间字符串
     */
    public static String formatDate2(Integer second) {
        if (second == null) {
            return null;
        }
        Date millisecondDate = new Date(second * 1000L);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        //设置起时间
        return formatter.format(millisecondDate);
    }

    /**
     * 获取尼日利亚0点时间戳（如果当前时间小于当前凌晨7点则取前一天7点)
     * @return 尼日利亚0点时间戳
     */
    public static int getNigeriaZeroTimestamp() {
        int todaySevenTimestamp = getTodayAndFixedTime("07:00:00");
        int curSecond = getCurSecond();
        if (curSecond >= todaySevenTimestamp) {
            return todaySevenTimestamp;
        }
        //获取昨天7点
        return todaySevenTimestamp - (60 * 60 * 24);
    }

    /**
     * 获取指定时间24小时之前的时间戳
     * @return 24小时之前的时间戳
     */
    public static int getLastDayByTime(Integer time) {
        if (time == null) {
            time = getCurSecond();
        }
        return time - (TimeUtils.DAY / 1000);
    }

    /**
     * 获取指定时间24小时之后的时间戳
     * @return 24小时之后的时间戳
     */
    public static int getNextDayByTime(Integer time) {
        if (time == null) {
            time = getCurSecond();
        }
        return time + (TimeUtils.DAY / 1000);
    }

    /**
     * 获取指定时间一个月之前的时间戳
     * @return 一个月之前的时间戳
     */
    public static int getLastMonthByTime(Integer time) {
        return getAddMonthByTime(time, -1);
    }

    public static int getAddMonthByTime(Integer time, int nums) {
        if (time == null) {
            time = getCurMonthFirstDay();
        }
        Calendar current = Calendar.getInstance();
        current.setTimeInMillis(time * 1000L);
        current.add(Calendar.MONTH, nums);
        return (int)(current.getTimeInMillis() / 1000);
    }

    /**
     * 获取指定时间一个月之后的时间戳
     * @return 一个月之后的时间戳
     */
    public static int getNextMonthByTime(Integer time) {
        return getAddMonthByTime(time, 1);
    }

    /**
     * 获取指定时间一个季度之前的时间戳
     * @return 一个月季度前的时间戳
     */
    public static int getLastSeasonByTime(Integer time) {
        if (time == null) {
            time = getCurMonthFirstDay();
        }
        Calendar current = Calendar.getInstance();
        current.setTimeInMillis(time * 1000L);
        current.add(Calendar.MONTH, -3);
        return (int)(current.getTimeInMillis() / 1000);
    }

    /**
     * 获取指定时间一年之前的时间戳
     * @return 一年前的时间戳
     */
    public static int getLastYearByTime(Integer time) {
        if (time == null) {
            time = getCurMonthFirstDay();
        }
        Calendar current = Calendar.getInstance();
        current.setTimeInMillis(time * 1000L);
        current.add(Calendar.YEAR, -1);
        return (int)(current.getTimeInMillis() / 1000);
    }

    /**
     * 获取指定时间一年之前的时间戳
     * @return 一年前的时间戳
     */
    public static int getNextYearByTime(Integer time) {
        if (time == null) {
            time = getCurYearFirstDay();
        }
        Calendar current = Calendar.getInstance();
        current.setTimeInMillis(time * 1000L);
        current.add(Calendar.YEAR, 1);
        return (int)(current.getTimeInMillis() / 1000);
    }

    /**
     * 当前时间添加 7 小时
     * @return 尼日利亚0点时间戳
     */
    public static int timeSubtract7hour(Date time) {
        return (int) (DateUtil.offsetHour(time, -7).getTime() / 1000);
    }

    public static String nigeriaDateformat(Long date ,String format) {
        Date millisecondDate = new Date(date);
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.ENGLISH);
        Calendar cal = Calendar.getInstance();
        //设置起时间
        cal.setTime(millisecondDate);
        cal.add(Calendar.HOUR, -7);
        return formatter.format(cal.getTime());
    }

    /**
     * 指定时间戳的 0点时间戳
     * @param myTime 时间戳
     * @return 指定时间戳的 0点时间戳
     */
    public static Long zeroDate(long myTime){
        Date date = new Date(myTime*1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        return zero.getTime()/1000;
    }

    /**
     * 以s为单位的时间段，转化成  58:23:44 时分秒写法
     * @param second 以秒为单位的时间段
     * @return 时分秒写法的字符串
     */
    public static String secondFormatToHMS(int second){
        if (second<=0) {
            return "00:00";
        }else {
            int min = second/60;
            int s = second%60;
            if(min < 60){
                return String.format("%02d:%02d", min, s);
            }
            int h = min/60;
            int m = min%60;
            return String.format("%d:%02d:%02d", h, m, s);
        }
    }



    /**
     * 获取当前时间尼日利亚季节开始时间
     */
    public static int seasonStartTime(Integer currentTime) {
        int currentTimeSeven = (int) (DateUtil.parse(DateUtil.format(new Date(currentTime * 1000L), "yyyy-MM-dd 07:00:00"), "yyyy-MM-dd HH:mm:ss").getTime() / 1000);
        currentTimeSeven = currentTime > currentTimeSeven ? currentTimeSeven : currentTimeSeven + DAY;
        // 获取当前季度开始时间
        String startTimeStr = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(currentTimeSeven * 1000L));
        int month = cal.get(Calendar.MONTH);
        startTimeStr = DateUtil.format(new Date(currentTimeSeven * 1000L), "yyyy");
        if (month >= 10) {
            startTimeStr = startTimeStr + "-10-01 07:00:00";
        } else if (month >= 7) {
            startTimeStr = startTimeStr + "-07-01 07:00:00";
        } else if (month >= 4) {
            startTimeStr = startTimeStr + "-04-01 07:00:00";
        } else {
            startTimeStr = startTimeStr + "-01-01 07:00:00";
        }
        return (int) (DateUtil.parse(startTimeStr, "yyyy-MM-dd HH:mm:ss").getTime() / 1000L);
    }

    /**
     * 获取当前时间尼日利亚季节开始时间
     */
    public static int monthStartTime(Integer currentTime) {
        int currentTimeSeven = (int) (DateUtil.parse(DateUtil.format(new Date(currentTime * 1000L), "yyyy-MM-dd 07:00:00"), "yyyy-MM-dd HH:mm:ss").getTime() / 1000);
        currentTimeSeven = currentTime > currentTimeSeven ? currentTimeSeven : currentTimeSeven + DAY;
        // 获取当前月开始时间
        Calendar currCal = Calendar.getInstance();
        currCal.setTime(new Date(currentTimeSeven * 1000L));
        currCal.set(Calendar.DAY_OF_MONTH, 1);
        currCal.set(Calendar.HOUR_OF_DAY, 7);
        currCal.set(Calendar.MINUTE, 0);
        currCal.set(Calendar.SECOND, 0);
        currCal.set(Calendar.MILLISECOND, 0);
        return (int) (currCal.getTimeInMillis() / 1000);
    }

    public static int yearmonthStartTime(Integer currentTime) {
        int nigeriaEndTime = (int) (DateUtil.parse(DateUtil.format(new Date(currentTime * 1000L), "yyyy-MM-dd 07:00:00"), "yyyy-MM-dd HH:mm:ss").getTime() / 1000);
        nigeriaEndTime = currentTime > nigeriaEndTime ? nigeriaEndTime : nigeriaEndTime + DAY;
        // 获取当前年的开始时间
        Calendar currCal = Calendar.getInstance();
        currCal.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        currCal.add(Calendar.YEAR, 0);
        currCal.add(Calendar.DATE, 0);
        currCal.add(Calendar.MONTH, 0);
        currCal.set(Calendar.DAY_OF_YEAR, 1);
        currCal.set(Calendar.HOUR_OF_DAY, 7);
        currCal.set(Calendar.MINUTE, 0);
        currCal.set(Calendar.SECOND, 0);
        currCal.set(Calendar.MILLISECOND, 0);
        return (int) (currCal.getTimeInMillis() / 1000);
    }
}