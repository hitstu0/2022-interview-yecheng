package Analyst.TickStatistic;

import Tools.Data.Tick;

public interface statistic {
    void doStatistic(Tick tick);
    void stop();
}
