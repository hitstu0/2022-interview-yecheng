package Writer.BarWriter;

import java.io.FileWriter;
import java.io.IOException;

import Tools.Data.Bar;
import Tools.ExceptionUtil.Exceptions.ResultFileException;

public class FileBarWriter implements BarWriter{
    private final String PATH = "result.txt";
    private FileWriter writer;
    private static FileBarWriter INSTANCE = new FileBarWriter();
    private FileBarWriter() {
       try {
           writer = new FileWriter(PATH,true);
       } catch (IOException e) {
           throw new ResultFileException();
       }
    }
    public static FileBarWriter getInstance() {
       return INSTANCE;
    }
    @Override
    public void write(Bar bar) {
        try {
            writer.append(bar.toString() + "\n");
        } catch (IOException e) {
            throw new ResultFileException();
        }
    }

    @Override
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            throw new ResultFileException();
        }
    }
    
}
