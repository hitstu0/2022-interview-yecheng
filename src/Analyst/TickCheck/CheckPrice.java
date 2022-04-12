package Analyst.TickCheck;

import java.util.HashMap;
import java.util.Map;

import Tools.Data.Tick;
import Tools.ExceptionUtil.Exceptions.PriceException;

public class CheckPrice implements TickCheck{
    
    private Map<String,Double> lastTurnOverMap = new HashMap<>();
    /**
     * 检查LastPrice和Turnover
     */
    @Override
    public void check(Tick data) {
        checkLastPrice(data);
        checkTurnOver(data);
    }

    private void checkLastPrice(Tick data) {
        double price = data.getLastPrice();
        double upperLimitPrice = data.getUpperLimitPrice();
        double lowerLimitPrice = data.getLowerLimitPrice();
        if(upperLimitPrice < lowerLimitPrice) {
            throw new PriceException(data);
        }
        if(!(price >= lowerLimitPrice && price <= upperLimitPrice)) {
            throw new PriceException(data);
        }
    }
    private void checkTurnOver(Tick data) {
        String name = data.getInstrumentID();
        double turnover = data.getTurnover();
        Double old = lastTurnOverMap.get(name);
        if(old != null && turnover < old) {
            throw new PriceException(data);
        }
        lastTurnOverMap.put(name, turnover);
    }
}
