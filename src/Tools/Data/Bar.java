package Tools.Data;

import java.text.DecimalFormat;

public class Bar {
    private final String name;
    private double OpeningPrice; //开盘价
    private double ClosingPrice; //收盘价
    private double HighestPrice; //最高价
    private double LowestPrice; //最低价
    
    public Bar(String name) {
        this.name = name;
        OpeningPrice = -1;
        ClosingPrice = -1;
        HighestPrice = -1;
        LowestPrice = -1;
    }

    public String getName() {
        return name;
    }

    public double getOpeningPrice() {
        return OpeningPrice;
    }
    public void setOpeningPrice(double openingPrice) {
        OpeningPrice = openingPrice;
    }
    public double getClosingPrice() {
        return ClosingPrice;
    }
    public void setClosingPrice(double closingPrice) {
        ClosingPrice = closingPrice;
    }
    public double getHighestPrice() {
        return HighestPrice;
    }
    public void setHighestPrice(double highestPrice) {
        HighestPrice = highestPrice;
    }
    public double getLowestPrice() {
        return LowestPrice;
    }
    public void setLowestPrice(double lowestPrice) {
        LowestPrice = lowestPrice;
    }
    
    public void init(double p) {
        OpeningPrice = p;
        ClosingPrice = p;
        HighestPrice = p;
        LowestPrice = p;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.0");
        return "Bar [ name=" + name  + ", ClosingPrice=" + df.format(ClosingPrice) + ", HighestPrice=" +  df.format(HighestPrice) + ", LowestPrice=" +  df.format(LowestPrice)
                + ", OpeningPrice=" +  df.format(OpeningPrice)  + " ]";
    }
  
}
