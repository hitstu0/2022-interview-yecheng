import Analyst.TickAnalyst.GenericTickAnalyst;
import Analyst.TickAnalyst.TickAnalyst;
import Reader.TickReader.ParquetTickReader;
import Reader.TickReader.TickReader;
import Writer.BarWriter.BarWriter;
import Writer.BarWriter.FileBarWriter;

public class App {

    public static void main(String[] args) {
        BarWriter writer = FileBarWriter.getInstance();
        TickAnalyst analyst = new GenericTickAnalyst(writer);
        TickReader reader = new ParquetTickReader(analyst);
        reader.read();
        writer.close();
    }
}