import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import javax.imageio.ImageIO;
public class MakeMirror{
    public BufferedImage Mirror(BufferedImage image){
        //get source image dimension
        int width = image.getWidth();
        int height = image.getHeight();
        //BufferedImage for mirror image
        BufferedImage newImage = new BufferedImage(width*2, height, BufferedImage.TYPE_INT_ARGB);
        //create mirror image pixel by pixel
        for(int y = 0; y < height; y++){
            for(int lx = 0, rx = width*2 - 1; lx < width; lx++, rx--){
                //lx starts from the left side of the image
                //rx starts from the right side of the image
                //get source pixel value
                int p = image.getRGB(lx, y);
                //set mirror image pixel value - both left and right
                //newImage.setRGB(lx, y, p);
                newImage.setRGB(rx, y, p);
            }
        }
        return newImage;
    }
}