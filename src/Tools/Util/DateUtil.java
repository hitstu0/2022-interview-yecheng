package Tools.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Tools.Data.Tick;
import Tools.ExceptionUtil.Exceptions.TimeException;


public class DateUtil {
    //日期格式
    private static SimpleDateFormat yearToSecond = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
    private static SimpleDateFormat yearToMinute = new SimpleDateFormat("yyyyMMdd HH:mm");
    private static SimpleDateFormat yearToDay = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat hourToSecond = new SimpleDateFormat("HH:mm:ss");
    public static Date getDateFormatYearToSecond(Tick data,String dateString) {
        try {
            Date date = yearToSecond.parse(dateString);
            return date;
        } catch (ParseException e) {
            throw new TimeException(data);
        }
    }
    public static Date getDateFormatYearToMinute(Tick data,String dateString) {
       try {
          Date date = yearToMinute.parse(dateString);
          return date;
       } catch (ParseException e) {
          throw new TimeException(data);
       }
    }
    public static Date getDateFormatYearToDay(Tick data,String dateString) {
        try {
           Date date = yearToDay.parse(dateString);
           return date;
        } catch (ParseException e) {
           throw new TimeException(data);
        }
    }
    public static Date getDateFormatHourToSecond(Tick data,String dateString) {
        try {
            Date date = hourToSecond.parse(dateString);
            return date;
         } catch (ParseException e) {
            throw new TimeException(data);
         }
    }
    public static Date getDateConverFromMillisecond(Tick data,long l) {
        Date date = new Date(l);
        return date;
    }
    public static int getDayIntervel(long day1,long day2) {
        long inter = day1 - day2;
        return (int)(inter/( 1000 * 60 * 60 * 24));
    }
}
