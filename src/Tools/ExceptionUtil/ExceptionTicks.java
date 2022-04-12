package Tools.ExceptionUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import Tools.Data.Tick;
import Tools.ExceptionUtil.Exceptions.ResultFileException;
/**
 * 保存异常Tick的工具类
 */
public class ExceptionTicks {
    //记录时间异常的tick
    private static List<Tick> TimeExceptionTicks;
    //记录价格异常的tick
    private static List<Tick> PriceExceptionTicks;
    //记录成交量异常的tick
    private static List<Tick> volumeExceptionTicks;
    
    public static void addTimeException(Tick t) {
        if(TimeExceptionTicks == null) {
            TimeExceptionTicks = new LinkedList<>();
        } 
        TimeExceptionTicks.add(t);
    }
    public static void addPriceException(Tick t) {
        if(PriceExceptionTicks == null) {
            PriceExceptionTicks = new LinkedList<>();
        }
        PriceExceptionTicks.add(t);
    }
    public static void addVolumeException(Tick t) {
        if(volumeExceptionTicks == null) {
            volumeExceptionTicks = new LinkedList<>();
        }
        volumeExceptionTicks.add(t);
    }
    public static void stop() {
        try {
           FileWriter writer = new FileWriter("ExceptionTicks.txt",true);
           if(TimeExceptionTicks != null) {
              for(Tick tick : TimeExceptionTicks) {
                  writeTicks(tick, writer);
              }
           }
           if(PriceExceptionTicks != null) {
              for(Tick tick : PriceExceptionTicks) {
                 writeTicks(tick, writer);
              }
           } 
           if(volumeExceptionTicks != null) {
              for(Tick tick : volumeExceptionTicks) {
                 writeTicks(tick, writer);
              }
           }
           writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultFileException();
        }
    }
    private static void writeTicks(Tick tick,FileWriter writer) throws IOException{
        writer.append(tick.toString() + "\n");
    }
}
