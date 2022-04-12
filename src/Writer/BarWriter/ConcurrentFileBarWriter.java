package Writer.BarWriter;


import java.io.IOException;
import java.util.*;
import Tools.Data.Bar;
import Tools.ExceptionUtil.Exceptions.ResultFileException;
import Writer.Thread.task;

public class ConcurrentFileBarWriter implements BarWriter{
    private List<String> pathList = new ArrayList<>(2);
    private List<task> taskList = new ArrayList<>(2);
    private static ConcurrentFileBarWriter INSTANCE = new ConcurrentFileBarWriter();
    private ConcurrentFileBarWriter() {
        pathList.add("resultA.txt");
        pathList.add("resultB.txt");
        for(int i = 0 ; i < 2 ; ++ i) {
            task t = new task(pathList.get(i));
            taskList.add(t);
            t.start();
        }
    }
    
    public static ConcurrentFileBarWriter getInstance() {
        return INSTANCE;
    }

    @Override
    public void write(Bar bar) {
        int hex = bar.getName().hashCode();
       int index = hex & 0x1;
       taskList.get(index).write(bar);
    }

    public void close(){
       for(int i = 0 ; i < taskList.size(); ++ i) {
           try {
               taskList.get(i).stopTask();
           } catch (IOException e) {
               throw new ResultFileException();
           }
       }
    }
    
}
