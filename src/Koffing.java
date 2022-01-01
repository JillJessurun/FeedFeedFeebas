import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageFilter;
import java.awt.image.RGBImageFilter;
import java.awt.image.WritableRaster;

public class Koffing extends GameObject{

    private Handler handler;
    private BufferedImage koffing;
    private MakeTransparent makeTransparent;
    private MakeMirror makeMirror;
    private boolean right = true;
    private boolean left = false;

    public Koffing(float x, float y, ID id, Handler handler, BufferedImage koffing, MakeTransparent makeTransparent, MakeMirror makeMirror) {
        super(x, y, id);
        this.handler = handler;
        this.koffing = koffing;
        this.makeTransparent = makeTransparent;
        this.makeMirror = makeMirror;
        velX = 3;
        velY = 2;
    }

    public void tick() {
        x += velX;
        y += velY;

        if (y <= -30 || y >= Game.HEIGHT - 90) {
            velY *= -1;
        }
        if (x <= -20 || x >= Game.WIDTH - 100) {
            velX *= -1;
            if (right){
                right = false;
                left = true;
            }else{
                right = true;
                left = false;
            }
        }
    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int colour = koffing.getRGB(0, 0);
        Image image = makeTransparent.makeColorTransparent(koffing, new Color(colour));
        BufferedImage transparent = makeTransparent.imageToBufferedImage(image);

        if (right && !left){
            g2d.drawImage(makeMirror.Mirror(transparent), (int) x - 120, (int) y, null);
        }else{
            g2d.drawImage(transparent, (int) x, (int) y, null);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x+ 18, (int) y + 21, 83, 73);
    }
}
