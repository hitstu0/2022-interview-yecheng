package Analyst.TickStatistic;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Tools.Data.Tick;
import Tools.ExceptionUtil.Exceptions.ResultFileException;
import Tools.Util.DateUtil;

public class DateRelationshipStatistic implements statistic{
    private String name;
    private static final String PATH = "dayRelation.txt";
    private List<dateRelationShip> list = new ArrayList<>(24);
    private List<dateRelationShip> tlist = new ArrayList<>(24);
    {
        for(int i = 0 ; i < 24 ; ++ i) {
            list.add(null);
        }
        for(int i = 0 ; i < 24 ; ++ i) {
            tlist.add(null);
        }
    }
    public DateRelationshipStatistic(String name) {
        this.name = name;
    }
    /**
     * ActionDay和TradingDay和实际交易时间的关系
     */
    @Override
    public void doStatistic(Tick tick) {
        int hour = getRealDayHour(tick);
        long realDate = getRealDate(tick,hour);
        actionDay(tick,hour,realDate);
        TradingDay(tick,hour,realDate);
    }
    /**
     * 获得当前小时
     * @param tick
     * @return
     */
    private int getRealDayHour(Tick tick) {
        Date date = DateUtil.getDateFormatHourToSecond(tick, tick.getUpdateTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
    /**
     * 获得实际时间的日期
     * @param tick
     * @return
     */
    private long getRealDate(Tick tick,int hour) {
        boolean err = false;
        Date realDate = DateUtil.getDateConverFromMillisecond(tick, tick.getLocalTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(realDate);
        if(hour == 23 && calendar.get(Calendar.HOUR_OF_DAY) == 0) {
            err = true;
        }
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND, 0);
        return err ? calendar.getTimeInMillis() - 1000*60*60*24 : calendar.getTimeInMillis();
    }
    /**
     * 分析每小时actionday和实际时间的关系
     * @param tick
     * @param hour
     * @param realDate
     */
    private void actionDay(Tick tick,int hour,long realDate) {
        dateRelationShip ship = list.get(hour);
        long actionDate = getActionDate(tick);
        int interval = DateUtil.getDayIntervel(actionDate, realDate);
        if(ship == null) {
            ship = new dateRelationShip();
            ship.setNums(1);
            ship.setDifference(interval);
            list.set(hour, ship);
        } else {
            int newNums = ship.getNums() + 1;
            ship.setNums(newNums);
            int newDifference = ship.getDifference() + interval;
            ship.setDifference(newDifference);
        }
    }
    private long getActionDate(Tick tick) {
        String actionDayString = tick.getActionDay();
        Date actionDay = DateUtil.getDateFormatYearToDay(tick, actionDayString);
        return actionDay.getTime();
    }


    /**
     * 分析每小时tradingday和实际时间的关系
     * @param tick
     * @param hour
     * @param realDate
     */
    private void TradingDay(Tick tick,int hour,long realDate) {
        dateRelationShip ship = tlist.get(hour);
        long tradingDate = getTradingDate(tick);
        int interval = DateUtil.getDayIntervel(tradingDate, realDate);
        if(ship == null) {
            ship = new dateRelationShip();
            ship.setNums(1);
            ship.setDifference(interval);
            tlist.set(hour, ship);
        } else {
            int newNums = ship.getNums() + 1;
            ship.setNums(newNums);
            int newDifference = ship.getDifference() + interval;
            ship.setDifference(newDifference);
        }
    }
    private long getTradingDate(Tick tick) {
        String tradingDateString = tick.getTradingDay();
        Date actionDay = DateUtil.getDateFormatYearToDay(tick, tradingDateString);
        return actionDay.getTime();
    }


    @Override
    public void stop() {
        FileWriter writer = null;
        try {
           writer = new FileWriter(PATH, true);
           writer.append("\n" + name + "\n");
           writer.append("每小时ActionDay和实际时间的平均相差天数" + "\n");
           for(int i = 0 ; i < 24 ; ++ i) {
               dateRelationShip ship = list.get(i);
               if(ship == null) {
                  writer.append("第 " + i + " 小时暂无数据\n");
               } else {
                  writer.append("第 " + i + " 小时为:" + (ship.getDifference()/(ship.getNums())) + "\n");
               }
           }
           writer.append("每小时tradingDay和实际时间的平均相差天数" + "\n");
           for(int i = 0 ; i < 24 ; ++ i) {
               dateRelationShip ship = tlist.get(i);
               if(ship == null) {
                  writer.append("第 " + i + " 小时暂无数据\n");
               } else {
                  writer.append("第 " + i + " 小时为:" + (ship.getDifference()/(ship.getNums())) + "\n");
               }
           }
           writer.close();
        } catch (IOException e) {
           throw new ResultFileException();
        } 
    }
    
    class dateRelationShip {
        private int nums;
        private int difference;
        public int getNums() {
            return nums;
        }
        public void setNums(int nums) {
            this.nums = nums;
        }
        public int getDifference() {
            return difference;
        }
        public void setDifference(int difference) {
            this.difference = difference;
        }
      
        
    }
}
