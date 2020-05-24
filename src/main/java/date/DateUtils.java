package date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import enums.Season;

public final class DateUtils {

    /**
     * 功能：根据生日计算年龄
     * 示例：现在是2020-4-23，如果生日是2020-5-20，那结果是19
     *
     * @param birthday 生日
     * @return 年龄
     */
    public static int getAge(Date birthday) {
        //初始化年龄
        int age = 0;
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        ////出生日期晚于当前时间, 不进行计算
        if (now.before(birthday)) {
            throw new IllegalArgumentException("出生日期晚于当前时间!");
        }
        //当前年份
        int yearNow = now.get(Calendar.YEAR);
        //当前月份
        int monthNow = now.get(Calendar.MONTH);
        //当前日期
        int dayOfMonthNow = now.get(Calendar.DAY_OF_MONTH);
        now.setTime(birthday);
        int yearBirth = now.get(Calendar.YEAR);
        int monthBirth = now.get(Calendar.MONTH);
        int dayOfMonthBirth = now.get(Calendar.DAY_OF_MONTH);
        //计算通过年获取的年龄
        age = yearNow - yearBirth;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                //当前日期在生日之前，年龄减一
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                //月份相同,但当前月份在生日之前，年龄减一
                age--;
            }
        }
        return age;
    }

    /**
     * 功能：给定时间对象，获取该时间的月初1日0时0分0秒0毫秒
     * 示例：2018-11-11 08:30:16 → 2018-11-01 00:00:00
     * 使用场景：可用在数据库里查询某月的时间范围
     *
     * @param src 源时间
     * @return 月初时间
     */
    public static Date getFirstDayOfMonth(Date src) {
        Calendar firstDayOfMonth = Calendar.getInstance();
        firstDayOfMonth.setTime(src);
        //设置为1号,当前日期既为本月第一天
        firstDayOfMonth.set(Calendar.DAY_OF_MONTH, 1);
        //将小时至0
        firstDayOfMonth.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        firstDayOfMonth.set(Calendar.MINUTE, 0);
        //将秒至0
        firstDayOfMonth.set(Calendar.SECOND, 0);
        //将毫秒至0
        firstDayOfMonth.set(Calendar.MILLISECOND, 0);
        return firstDayOfMonth.getTime();
    }

    /**
     * 功能：给定时间对象，获取该时间的月末最后一天的23时23分59秒999毫秒
     * 示例：2018-11-11 08:30:16 → 2018-11-30 23:23:59
     * 使用场景：可用在数据库里查询某月的时间范围
     *
     * @param src 源时间
     * @return 月末时间
     */
    public static Date getLastDayOfMonth(Date src) {
        Calendar larstDayOfMonth = Calendar.getInstance();
        larstDayOfMonth.setTime(src);
        //设置为当月最后一天
        larstDayOfMonth.set(Calendar.DAY_OF_MONTH, larstDayOfMonth.getActualMaximum(Calendar.DAY_OF_MONTH));
        //将小时至23
        larstDayOfMonth.set(Calendar.HOUR_OF_DAY, 23);
        //将分钟至59
        larstDayOfMonth.set(Calendar.MINUTE, 59);
        //将秒至59
        larstDayOfMonth.set(Calendar.SECOND, 59);
        //将毫秒至999
        larstDayOfMonth.set(Calendar.MILLISECOND, 999);
        return larstDayOfMonth.getTime();
    }

    /**
     * 功能：获取当前日期指定天数之前或之后的日期，如果参数为负数，则往前回滚。
     * 示例1：当前是2018-08-08，参数days=5，结果是2018-08-13
     * 示例2：当前是2018-08-08，参数days=-4，结果是2018-08-04
     *
     * @param days 偏移的天数，如果为负数则往前回滚。
     * @return 新日期
     */
    public static Date offsetDate(int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }

    /**
     * 功能：获取当前季节，如春季、夏季、秋季、冬季。
     *
     * @return 季节
     */
    public static Season getCurrentSeason() {
        //创建Calendar类来获取时间信息
        Calendar calendar = Calendar.getInstance();
        //获取当前时间
        calendar.setTime(new Date());
        //判断当前时间月份
        switch (calendar.get(Calendar.MONTH)) {
            case 3:
            case 4:
            case 5:
                return Season.SPRING;
            case 6:
            case 7:
            case 8:
                return Season.SUMMER;
            case 9:
            case 10:
            case 11:
                return Season.AUTUMN;
            case 12:
            case 1:
            case 2:
                return Season.WINTER;
            default:
                return null;
        }
    }

    /**
     * 功能：获取人性化时间，例如5分钟之内则显示“刚刚”，其它显示16分钟前、2小时前、3天前、4月前、5年前等
     *
     * @param date 源时间。
     * @return 人性化时间
     */
    public static String getDisplayTime(Date date) {
        //创建空字符串用来存储人性化时间信息
        String r = "";
        //判断日期是否为空
        if (date == null) {
            return r;
        }
        //获取当前时间的秒数
        long nowTimeLong = System.currentTimeMillis();
        //获取传入时间的秒数
        long dateTimeLong = date.getTime();
        //获取两个时间的时间差, 单位毫秒
        long result = Math.abs(nowTimeLong - dateTimeLong);
        //如果时间在一分钟内
        if (result < 60000) {
            long seconds = result / 1000;
            r = "刚刚";
            //时间在一小时内
        } else if (result >= 60000 && result < 3600000) {
            long seconds = result / 60000;
            r = seconds + "分钟前";
            //时间在一天内
        } else if (result >= 3600000 && result < 86400000) {
            long seconds = result / 3600000;
            r = seconds + "小时前";
            //时间在三十天内
        } else if (result >= 86400000 && result < 1702967296) {
            long seconds = result / 86400000;
            r = seconds + "天前";
            //不在上面范围的显示传入的时间
        } else {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            r = df.format(date).toString();
        }
        return r;
    }
}
