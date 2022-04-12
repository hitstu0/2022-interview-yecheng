package Writer.BarWriter;

import Tools.Data.Bar;

public interface BarWriter {
    void write(Bar bar);
    void close();
}
