package Analyst.TickStatistic;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import Tools.Data.Tick;

/**
 * 统计日期的适配器
 */
public class DateStatisticAdapter implements statistic{
    private Map<String,DateRelationshipStatistic> map = new HashMap<>();
    @Override
    public void doStatistic(Tick tick) {
        String name = tick.getExchangeID();
        DateRelationshipStatistic dr = null;
        if((dr = map.get(name)) == null) {
            dr = new DateRelationshipStatistic(name);
            map.put(name, dr);
        }
        dr.doStatistic(tick);
    }

    @Override
    public void stop() {
        Collection<DateRelationshipStatistic> list = map.values();
        for (DateRelationshipStatistic dateRelationshipStatistic : list) {
            dateRelationshipStatistic.stop();
        }
        
    }
    
}
