import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Writer {

    private PrintWriter printWriter;

    public Writer(int startLoop) {
        int endLoop = startLoop + 9;
        String path = "res/numbers" + startLoop + "-" + endLoop + ".txt";
        try {
            printWriter = new PrintWriter(path);
        }
        catch (FileNotFoundException ex)    {
            ex.printStackTrace();
        }

    }

    public void write(String data) {
        printWriter.write(data);
    }

    public void stopWrite() {
        printWriter.flush();
        printWriter.close();
    }
}
