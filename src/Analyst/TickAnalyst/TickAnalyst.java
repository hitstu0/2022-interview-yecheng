package Analyst.TickAnalyst;

import Tools.Data.Tick;

public interface TickAnalyst {
    void analyze(Tick tick);
    void stop();
}
