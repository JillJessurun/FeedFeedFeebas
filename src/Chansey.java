import java.awt.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

public class Chansey extends GameObject{
    private Handler handler;
    private BufferedImage chansey;
    private MakeTransparent makeTransparent;
    private MakeMirror makeMirror;
    private HUD hud;
    private boolean right = true;
    private boolean left = false;

    public Chansey(float x, float y, ID id, Handler handler, BufferedImage chansey, MakeTransparent makeTransparent, MakeMirror makeMirror, HUD hud) {
        super(x, y, id);
        this.handler = handler;
        this.chansey = chansey;
        this.makeTransparent = makeTransparent;
        this.makeMirror = makeMirror;
        this.hud = hud;
        velX = 2;
        velY = 1;
    }

    public void tick() {
        x += velX;
        y += velY * 3;

        if (y <= Game.HEIGHT - 224 || y >= Game.HEIGHT - 200) {
            velY *= -1;
        }
        if (x <= -60 || x >= Game.WIDTH - 180) {
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
        int colour = chansey.getRGB(0, 0);
        Image image = makeTransparent.makeColorTransparent(chansey, new Color(colour));
        BufferedImage transparent = makeTransparent.imageToBufferedImage(image);

        if (right && !left){
            g2d.drawImage(makeMirror.Mirror(transparent), (int) x - 240, (int) y, null);
        }else{
            g2d.drawImage(transparent, (int) x, (int) y, null);
        }

        if (hud.getCHANSEYRATE() == 1000) {
            Font font = new Font("arial", Font.BOLD, 60);
            g2d.setColor(Color.white);
            g2d.setFont(font);
            g2d.drawString("!", (int) x + 110, (int) y + 40);
        }

        //g2d.setColor(Color.red);
        //g2d.drawRect((int) x + 60, (int) y + 60, 120, 115);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x + 60, (int) y + 60, 120, 115);
    }
}
