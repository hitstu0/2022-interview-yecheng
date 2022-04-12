package Analyst.TickAnalyst;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Tools.Data.Bar;
import Tools.Data.Tick;
import Tools.Util.DateUtil;
import Writer.BarWriter.BarWriter;


public class GenericTickAnalyst implements TickAnalyst{
    private BarWriter writer;
    //保存每个合约的bar
    private Map<String,Bar> barMap = new HashMap<>();
    //保存每个合约当前bar所属的时间（分钟），用于判断是否重新生成新的bar
    private Map<String,Date> dateMap = new HashMap<>();
    
    /**
     * 解析tick的方法，包括初始化信息和实际更新bar两个步骤
     */
    public GenericTickAnalyst(BarWriter writer) {
        this.writer = writer;
    }
    @Override
    public void analyze(Tick tick) {
        initMap(tick);
        updateBar(tick);
    }
    /**
     * 根据tick的合约名字和UpdateTime，更新Map
     * @param tick
     */
    private void initMap(Tick tick) {
       String name = tick.getInstrumentID();
       Date date = DateUtil.getDateFormatYearToMinute(tick,tick.getActionDay() + " " + tick.getUpdateTime());
       doInitMapIfBarNotExist(name, date, tick);
       doInitMapIfBarOutDated(name, date, tick);
       
    }  

    /**
     * 如果该合约是第一次到达则初始化Map
     * @param name
     * @param date
     * @param tick
     */
    private void doInitMapIfBarNotExist(String name,Date date,Tick tick){
       Bar bar = null;
       if((bar = barMap.get(name)) == null) {
           bar = new Bar(name);
           dateMap.put(name, date);  
           barMap.put(name, bar);
           bar.init(tick.getLastPrice());
       } 
    }
    /**
     * 如果该合约的bar已过期就保存并生成新的bar
     * @param name
     * @param date
     * @param tick
     */
    private void doInitMapIfBarOutDated(String name,Date date,Tick tick){
       Bar bar = barMap.get(name);
       Date oldDate = dateMap.get(name);
       long newDateMill = date.getTime();
       long oldDateMill = oldDate.getTime();
       if(newDateMill != oldDateMill) {
           oldDate.setTime(newDateMill);
           writer.write(bar);
           bar.init(tick.getLastPrice());  
       }
    }
    
    /**
     * 负责根据tick的价格更新bar的价格
     * @param tick
     */
    private void updateBar(Tick tick) {
        String name = tick.getInstrumentID();
        Bar bar = barMap.get(name);
        double price = tick.getLastPrice();
        updatePrice(bar, price);
    }  
    private void updatePrice(Bar bar,double price) {
        bar.setClosingPrice(price);
        if(price > bar.getHighestPrice()) {
            bar.setHighestPrice(price);
        } else {
            if(price < bar.getLowestPrice()) {
                bar.setLowestPrice(price);
            }
        }
    }
    /**
     * 处理清尾工作
     */
    @Override
    public void stop() {
        Collection<Bar> collections = barMap.values();
        for (Bar bar : collections) {
            writer.write(bar);
        }
    }
}
