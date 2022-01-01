import java.awt.*;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Glalie extends GameObject{
    private Handler handler;
    private BufferedImage glalie;
    private MakeTransparent makeTransparent;
    private MakeMirror makeMirror;
    private boolean right = true;
    private boolean left = false;

    public Glalie(float x, float y, ID id, Handler handler, BufferedImage glalie, MakeTransparent makeTransparent, MakeMirror makeMirror) {
        super(x, y, id);
        this.handler = handler;
        this.glalie = glalie;
        this.makeTransparent = makeTransparent;
        this.makeMirror = makeMirror;
        velX = 4;
        velY = 3;
    }

    public void tick() {
        x += velX;
        y += velY;

        if (y <= -25 || y >= Game.HEIGHT - 82) {
            velY *= -1;
        }
        if (x <= -28 || x >= Game.WIDTH - 78) {
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
        int colour = glalie.getRGB(0, 0);
        Image image = makeTransparent.makeColorTransparent(glalie, new Color(colour));
        BufferedImage transparent = makeTransparent.imageToBufferedImage(image);

        if (right && !left){
            g2d.drawImage(makeMirror.Mirror(transparent), (int) x - 95, (int) y + 3, null);
        }else{
            g2d.drawImage(transparent, (int) x + 5, (int) y + 5, null);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x+ 27, (int) y + 27, 55, 55);
    }
}
