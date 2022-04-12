package Tools.ExceptionUtil.Exceptions;

import Tools.Data.Tick;
import Tools.ExceptionUtil.ExceptionHandler;
import Tools.ExceptionUtil.ExceptionTicks;

public class PriceException  extends RuntimeException implements ExceptionHandler{
    private Tick tick;
    public PriceException(Tick tick) {
       this.tick = tick;
    }

    @Override
    public void handle() {
        ExceptionTicks.addPriceException(tick);
    }
    
}
