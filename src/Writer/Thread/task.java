package Writer.Thread;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import Tools.Data.Bar;


public class task extends Thread {
   
    private String PATH;
    private boolean run;
    private FileWriter writer;
    private Vector<Bar> vector = new Vector<>();
    public task(String path) {
        this.PATH = path;
        run = true;
        try {
            writer = new FileWriter(PATH,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        while(run || !vector.isEmpty()) {
           if(!vector.isEmpty()) {
               Bar bar = vector.get(0);
               vector.remove(0);
               try {
                 writer.write(bar.toString() + "\n");
               } catch (IOException e) {
                  e.printStackTrace();
               }
           }
        }
    }
    public void write(Bar b) {
        vector.add(b);
    }
    public void stopTask() throws IOException{
        writer.close();
        run = false;
    }
}
