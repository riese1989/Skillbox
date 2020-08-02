import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteFile {
    private String line;
    private Integer countTabs;

    public WriteFile(String line, Integer tabs) {
        this.line = line;
        this.countTabs = tabs;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public Integer getCountTabs() {
        return countTabs;
    }

    public void setCountTabs(Integer countTabs) {
        this.countTabs = countTabs;
    }

    public void addTabs() {
        String tabs = new String();
        for (int i = 0; i < countTabs; i++) {
            tabs += "\t";
        }
        line = tabs + line + "\n";
    }

    public synchronized void export() throws IOException {
        FileWriter writer = new FileWriter("result.txt", true);
        BufferedWriter bufferWriter = new BufferedWriter(writer);
        addTabs();
        bufferWriter.write(line);
        bufferWriter.close();

    }
}
