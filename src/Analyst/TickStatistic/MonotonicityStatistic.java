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

public class MonotonicityStatistic implements statistic{
    private Map<String,Long> lastUpdateMillisec = new HashMap<>();
    private Map<String,Boolean> monotonicitys = new HashMap<>();
    private static final String PATH = "monotonicity.txt";
    /**
     * 统计单调性
     */
    @Override
    public void doStatistic(Tick data) {
        String name = data.getExchangeID();
        Boolean monotonicity = null;
        if((monotonicity = monotonicitys.get(name)) != null && monotonicity == false) {
            return;
        }
        String stringDate = data.getActionDay() + " " + data.getUpdateTime();
        Date date = DateUtil.getDateFormatYearToSecond(data,stringDate);
        long newMill = date.getTime() + data.getUpdateMillisec();
        Long oldMill = lastUpdateMillisec.get(name);
        if(oldMill != null && oldMill > newMill) {
            monotonicitys.put(name, false);
        } else {
           if(oldMill == null) {
            monotonicitys.put(name,true);
           } 
        }
        lastUpdateMillisec.put(name, newMill);
        
    }

    @Override
    public void stop() {
        try {
            FileWriter writer = new FileWriter(PATH,true);
            Set<Entry<String,Boolean>> keys = monotonicitys.entrySet();
            for (Entry<String,Boolean> entry : keys) {
                writer.append(entry.getKey() + " " + entry.getValue() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new ResultFileException();
        }
        
    }
    
}
