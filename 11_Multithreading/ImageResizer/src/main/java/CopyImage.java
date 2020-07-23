import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class CopyImage extends Thread {
    long start;
    File[] files;
    String dstFolder;

    public CopyImage(File[] files, String dstFolder, long start) {
        this.files = files;
        this.dstFolder = dstFolder;
        this.start = start;
    }

    @Override
    public void run()   {
        try
        {
            for(File file : files)
            {
                if (file.isDirectory()) {
                    continue;
                }
                BufferedImage image = ImageIO.read(file);
                int newWidth = 300;
                BufferedImage newImg = Scalr.resize(image,newWidth);
                if(image == null) {
                    continue;
                }
                File newFile = new File(dstFolder + "/" + file.getName());
                ImageIO.write(newImg,"jpeg", newFile);
            }
            System.out.println("Duration: " + (System.currentTimeMillis() - start));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
