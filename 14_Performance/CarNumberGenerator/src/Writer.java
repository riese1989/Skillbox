import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Writer implements Runnable {

    private static PrintWriter writer, writer2, writer3;

    static {
        try {
            writer = new PrintWriter("res/numbers.txt");
            writer2 = new PrintWriter("res/numbers2.txt");
            writer3 = new PrintWriter("res/numbers3.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String data;

    public Writer(String data) {
        this.data = data;
    }

    @Override
    public void run() {
        try {
            write(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static synchronized void write(String data) throws FileNotFoundException {
        writer.write(data);
        writer2.write(data);
        writer3.write(data);
    }

    public static void close() {
        writer.flush();
        writer.close();
        writer2.flush();
        writer2.close();
        writer3.flush();
        writer3.close();
    }
}
