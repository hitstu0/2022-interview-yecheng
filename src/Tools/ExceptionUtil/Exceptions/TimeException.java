package Tools.ExceptionUtil.Exceptions;

import Tools.Data.Tick;
import Tools.ExceptionUtil.ExceptionHandler;
import Tools.ExceptionUtil.ExceptionTicks;

public class TimeException extends RuntimeException implements ExceptionHandler{
    private Tick tick;
    public TimeException(Tick tick) {
       this.tick = tick;
    }

    @Override
    public void handle() {
        ExceptionTicks.addTimeException(tick);
    }
    
}
