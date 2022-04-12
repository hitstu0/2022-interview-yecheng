package Analyst.TickStatistic;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import Tools.Data.Tick;
import Tools.ExceptionUtil.Exceptions.ResultFileException;
import Tools.Util.DateUtil;

public class PushFrequentStatistic implements statistic{
    
    private Map<String,PushFrequent> map = new HashMap<>();
    private static final String PATH = "pushFrequent.txt";
 
    /**
     * 统计每个交易所的推送频率
     */
    @Override
    public void doStatistic(Tick tick) {
 
        String name = tick.getExchangeID();
        PushFrequent pf = map.get(name);
        Date arriveDate = DateUtil.getDateFormatYearToSecond(tick, tick.getActionDay() + " " + tick.getUpdateTime());
        long arriveTime = arriveDate.getTime() + tick.getUpdateMillisec();
        if(pf == null) {
            pf = new PushFrequent();
            pf.setTicksNum(1);
            pf.setFirstTime(arriveTime);
            pf.setEndTime(arriveTime);
            map.put(name, pf);
        } else {
            long nums = pf.getTicksNum() + 1;
            pf.setTicksNum(nums);
            pf.setEndTime(Math.max(pf.getEndTime(),arriveTime));
            pf.setFirstTime(Math.min(pf.getFirstTime(),arriveTime));
        }
    }

    @Override
    public void stop() {
        try {
            FileWriter writer = new FileWriter(PATH,true);
            Set<Entry<String,PushFrequent>> keys = map.entrySet();
            for (Entry<String,PushFrequent> entry : keys) {
                PushFrequent pf = entry.getValue();
                double frequent = ( ((double)pf.getTicksNum() - 1) *1000) / (pf.getEndTime() - pf.getFirstTime());
                writer.append(entry.getKey() + " " + frequent + "\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new ResultFileException();
        }
    }
    
    class PushFrequent {
        private long firstTime;
        private long endTime;
        private long ticksNum;
       
        public long getEndTime() {
            return endTime;
        }
        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }
        public long getFirstTime() {
            return firstTime;
        }
        public void setFirstTime(long firstTime) {
            this.firstTime = firstTime;
        }
        public long getTicksNum() {
            return ticksNum;
        }
        public void setTicksNum(long ticksNum) {
            this.ticksNum = ticksNum;
        }
    }
}
