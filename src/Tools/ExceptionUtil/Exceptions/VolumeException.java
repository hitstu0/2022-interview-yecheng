package Tools.ExceptionUtil.Exceptions;

import Tools.Data.Tick;
import Tools.ExceptionUtil.ExceptionHandler;
import Tools.ExceptionUtil.ExceptionTicks;

public class VolumeException extends RuntimeException implements ExceptionHandler{
    private Tick tick;
    public VolumeException(Tick tick) {
       this.tick = tick;
    }

    @Override
    public void handle() {
        ExceptionTicks.addVolumeException(tick);
    }
}
