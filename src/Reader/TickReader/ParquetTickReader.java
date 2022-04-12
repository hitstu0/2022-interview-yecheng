package Reader.TickReader;

import java.util.LinkedList;
import java.util.List;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import Analyst.TickAnalyst.TickAnalyst;
import Analyst.TickCheck.CheckPrice;
import Analyst.TickCheck.CheckTime;
import Analyst.TickCheck.CheckVolume;
import Analyst.TickCheck.TickCheck;
import Analyst.TickStatistic.DateStatisticAdapter;
import Analyst.TickStatistic.MonotonicityStatistic;
import Analyst.TickStatistic.PushFrequentStatistic;
import Analyst.TickStatistic.statistic;
import Tools.Data.Tick;
import Tools.ExceptionUtil.ExceptionHandler;
import Tools.ExceptionUtil.ExceptionTicks;
import Tools.Util.ParquetUtil;
/**
 * 负责读取Parquet文件并调用分析方法
 */
public class ParquetTickReader implements TickReader {

    private TickAnalyst analyst;
    private List<statistic> statistic = new LinkedList<>();
    private List<TickCheck> tickChecks = new LinkedList<>();
    public ParquetTickReader(TickAnalyst analyst) {
       this.analyst = analyst;
       statistic.add(new DateStatisticAdapter());
       statistic.add(new PushFrequentStatistic());
       statistic.add(new MonotonicityStatistic());
       tickChecks.add(new CheckTime());
       tickChecks.add(new CheckPrice());
       tickChecks.add(new CheckVolume());
    }
    /**
     * 读取parquet文件并模拟流式接收
     */
    @Override
    public void read() {
        List<Row> rowList = getRowFromParquet();
        SimulateDataFlow(rowList);
    }

    /**
     * 将 parquet文件解析成List<Row>
     * @return
     */
    private List<Row> getRowFromParquet(){
        SparkSession session = SparkSession.builder().appName(ParquetUtil.APP_NAME).master(ParquetUtil.MASTER).getOrCreate();
        Dataset<Row> set =  session.read().parquet(ParquetUtil.PARQUET_URL);
        set.show();
        List<Row> rowList = set.collectAsList();
        return rowList;
    }
    /**
     * 模拟流接收
     * @param rowList
     */
    private void SimulateDataFlow(List<Row> rowList) {
        long time = System.currentTimeMillis();
        for (Row row : rowList) {
            try {
               Tick tick = RowToTick(row);
               Check(tick);
               Statistic(tick);
               analyst.analyze(tick);
            } catch(Exception e) {
               if(e instanceof ExceptionHandler) {
                   ExceptionHandler handler = (ExceptionHandler)e;
                   handler.handle();
               } else {
                   stop();
                   throw e;
               }
            } 
        }
        stop();
        System.out.println((System.currentTimeMillis() - time));
    }
    
    /**
     * 将Row类型转换为Tick类型
     * 用反射虽然能够减少代码量，但效率过低
     * @param row
     * @return
     */
    private Tick RowToTick(Row row) {
        Tick tick = new Tick();
        for(int i = 0 ; i < 12; ++ i) {
            switch(i) {
                case 0 :
                tick.setLocalTime(row.getLong(i));
                break;
                case 1 :
                tick.setTradingDay(row.getString(i));
                break;
                case 2 :
                tick.setInstrumentID(row.getString(i));
                break;
                case 3 :
                tick.setExchangeID(row.getString(i));
                break;
                case 4 :
                tick.setLastPrice(row.getDouble(i));
                break;
                case 5 :
                tick.setVolume(row.getInt(i));
                break;
                case 6 :
                tick.setTurnover(row.getDouble(i));
                break;
                case 7 :
                tick.setUpperLimitPrice(row.getDouble(i));
                break;
                case 8 :
                tick.setLowerLimitPrice(row.getDouble(i));
                break;
                case 9 :
                tick.setUpdateTime(row.getString(i));
                break;
                case 10 :
                tick.setUpdateMillisec(row.getInt(i));
                break;
                case 11 :
                tick.setActionDay(row.getString(i));
                break;
            }
        }
        return tick;
    }

    private void Check(Tick tick) {
       for(int i = 0 ; i < tickChecks.size() ; ++ i) {
          TickCheck check = tickChecks.get(i);
          check.check(tick);
       }
    }
    private void Statistic(Tick tick) {
       for(int i = 0 ; i < statistic.size() ; ++ i) {
           statistic st = statistic.get(i);
           st.doStatistic(tick);
       }
    }
    private void stop() {
        ExceptionTicks.stop();
       analyst.stop();
       for(int i = 0 ; i < statistic.size() ; ++ i) {
        statistic st = statistic.get(i);
        st.stop();
       }
    }
}
