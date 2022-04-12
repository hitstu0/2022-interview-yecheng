package Analyst.TickCheck;

import Tools.Data.Tick;
import Tools.ExceptionUtil.Exceptions.TimeException;
import Tools.Util.DateUtil;

public class CheckTime implements TickCheck{
    private long lastLocalTime = -1;
    
    
    /**
     * 检查日期相关的数据
     */
    @Override
    public void check(Tick data) {
        checkLocalTime(data);
        checkTradingDay(data);
        checkActionDay(data);
        checkUpdateTime(data);
    } 

    /**
     * 检查LocalTime
     * @param data
     */
    private void checkLocalTime(Tick data) {
       if(lastLocalTime > 0 && data.getLocalTime() <= lastLocalTime) {
           throw new TimeException(data);
       }
       data.setLocalTime(data.getLocalTime()/1000000);
       lastLocalTime = data.getLocalTime();
    }
    
    /**
     * 检查tradingDay
     * @param data
     */
    private void checkTradingDay(Tick data) {
       DateUtil.getDateFormatYearToDay(data, data.getTradingDay());
    }
    /**
     * 检查actionDay
     * @param data
     */
    private void checkActionDay(Tick data) {
       DateUtil.getDateFormatYearToDay(data, data.getActionDay());
    }
    private void checkUpdateTime(Tick data) {
        DateUtil.getDateFormatHourToSecond(data, data.getUpdateTime());
    }
 
}
