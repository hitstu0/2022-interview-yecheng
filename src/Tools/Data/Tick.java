package Tools.Data;


public class Tick {
    private long LocalTime; //本地时间
    private String TradingDay; //无说明
    private String InstrumentID; //合约名称
    private String ExchangeID; // 交易所
    private double LastPrice; //最新价格
    private int Volume; //累计成交价格
    private double Turnover; // 累计成交额
    private double UpperLimitPrice; // 最高限价
    private double LowerLimitPrice; // 最低限价
    private String UpdateTime; //交易所时间
    private int UpdateMillisec; // 交易所时间，毫秒
    private String ActionDay; // 无说明

    public long getLocalTime() {
        return LocalTime;
    }
    public void setLocalTime(long localTime) {
        LocalTime = localTime;
    }
    public double getLastPrice() {
        return LastPrice;
    }
    public void setLastPrice(double lastPrice) {
        LastPrice = lastPrice;
    }
    public double getTurnover() {
        return Turnover;
    }
    public void setTurnover(double turnover) {
        Turnover = turnover;
    }
    public double getUpperLimitPrice() {
        return UpperLimitPrice;
    }
    public void setUpperLimitPrice(double upperLimitPrice) {
        UpperLimitPrice = upperLimitPrice;
    }
    public void setVolume(int volume) {
        Volume = volume;
    }
    public double getLowerLimitPrice() {
        return LowerLimitPrice;
    }
    public void setLowerLimitPrice(double lowerLimitPrice) {
        LowerLimitPrice = lowerLimitPrice;
    }
    public String getTradingDay() {
        return TradingDay;
    }
    public void setTradingDay(String tradingDay) {
        TradingDay = tradingDay;
    }
    public String getInstrumentID() {
        return InstrumentID;
    }
    public void setInstrumentID(String instrumentID) {
        InstrumentID = instrumentID;
    }
    public String getExchangeID() {
        return ExchangeID;
    }
    public void setExchangeID(String exchangeID) {
        ExchangeID = exchangeID;
    }
  
    public String getUpdateTime() {
        return UpdateTime;
    }
    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }
    public void setLastPrice(float lastPrice) {
        LastPrice = lastPrice;
    }
    public long getVolume() {
        return Volume;
    }
    
    public void setLowerLimitPrice(float lowerLimitPrice) {
        LowerLimitPrice = lowerLimitPrice;
    }
    public int getUpdateMillisec() {
        return UpdateMillisec;
    }
    public void setUpdateMillisec(int updateMillisec) {
        UpdateMillisec = updateMillisec;
    }
    public String getActionDay() {
        return ActionDay;
    }
    public void setActionDay(String actionDay) {
        ActionDay = actionDay;
    }
    @Override
    public String toString() {
        return "Tick [ActionDay=" + ActionDay + ", ExchangeID=" + ExchangeID + ", InstrumentID=" + InstrumentID
                + ", LastPrice=" + LastPrice + ", LocalTime=" + LocalTime + ", LowerLimitPrice=" + LowerLimitPrice
                + ", TradingDay=" + TradingDay + ", Turnover=" + Turnover + ", UpdateMillisec=" + UpdateMillisec
                + ", UpdateTime=" + UpdateTime + ", UpperLimitPrice=" + UpperLimitPrice + ", Volume=" + Volume + "]";
    }
}
