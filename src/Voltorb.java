import java.awt.*;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Voltorb extends GameObject{
    private Handler handler;
    private BufferedImage voltorb;
    private MakeTransparent makeTransparent;
    private MakeMirror makeMirror;
    private boolean right = false;
    private boolean left = true;

    public Voltorb(float x, float y, ID id, Handler handler, BufferedImage voltorb, MakeTransparent makeTransparent, MakeMirror makeMirror) {
        super(x, y, id);
        this.handler = handler;
        this.voltorb = voltorb;
        this.makeTransparent = makeTransparent;
        this.makeMirror = makeMirror;
        velX = 4;
        velY = 2;
    }

    public void tick() {

        x += velX;
        y += velY;

        if (y <= -45 || y >= Game.HEIGHT - 100) {
            velY *= -1;
        }
        if (x <= -45 || x >= Game.WIDTH - 100) {
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
        int colour = voltorb.getRGB(0, 0);
        Image image = makeTransparent.makeColorTransparent(voltorb, new Color(colour));
        BufferedImage transparent = makeTransparent.imageToBufferedImage(image);

        if (right && !left){
            g2d.drawImage(makeMirror.Mirror(transparent), (int) x - 150, (int) y, null);
        }else{
            g2d.drawImage(transparent, (int) x, (int) y, null);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x+ 44, (int) y + 44, 60, 58);
    }
}
