package Analyst.TickCheck;

import java.util.HashMap;
import java.util.Map;

import Tools.Data.Tick;
import Tools.ExceptionUtil.Exceptions.VolumeException;

public class CheckVolume implements TickCheck{
    
    private Map<String,Long> lastVolumeMap = new HashMap<>();
    /**
     * 检查成交量
     */
    @Override
    public void check(Tick data) {
        String name = data.getInstrumentID();
        long newVolume = data.getVolume();
        Long oldVolume = lastVolumeMap.get(name);
        if(oldVolume != null && newVolume < oldVolume) {
            throw new VolumeException(data);
        }
        lastVolumeMap.put(name, newVolume);
    }
    
}
