import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class Main
{
    public static void main(String[] args)
    {
        String srcFolder = "src";
        String dstFolder = "dst";
        int sizeArray;
        int currentPosition = 0;
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        int countCore = threadBean.getThreadCount();

        File srcDir = new File(srcFolder);

        long start = System.currentTimeMillis();

        File[] files = srcDir.listFiles();
        sizeArray = files.length / countCore;

        for (int core = 1; core <= countCore; core++)   {
            File[] file1 = new File[sizeArray];
            System.arraycopy(files, currentPosition,file1,0,file1.length);
            CopyImage copyImage = new CopyImage(file1,dstFolder, start);
            copyImage.start();
            currentPosition += sizeArray;
            if (countCore - core != 0) {
                sizeArray = (files.length - currentPosition) / (countCore - core);
            }
        }
    }
}
